import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';
import { NgClass, NgIf } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { ListboxModule } from 'primeng/listbox';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

interface Subject {
  subjectID: string,
  name: string;
}

interface TeacherClass {
  classID: string;
  subjectID: string;
  className: string;
  subjectName: string;
  quantity: number;
}

@Component({
  selector: 'app-teacher-edit',
  standalone: true,
  imports: [
    FormsModule,
    InputMaskModule,
    MultiSelectModule,
    NgClass,
    ToastModule,
    DialogModule,
    ListboxModule,
    NgIf,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './teacher-edit.component.html',
  styleUrls: ['./teacher-edit.component.scss']
})
export class TeacherEditComponent implements OnInit {
  isMenuOpen = false;
  visible: boolean = false;
  confirmEditVisible: boolean = false;
  amountOfClasses: number = 0
  maxClassesPerWeek: number = 0

  teacherClasses: TeacherClass[] = [];
  subjectsToRemove: Subject[] = [];

  selectedClasses: TeacherClass[] = [];
  teacherID: string = '';
  teacherName: string = '';
  teacherEmail: string = '';
  teacherPassword: string = '';
  teacherCPF: string = '';
  selectedSubjects: Subject[] = [];
  subjectsTrueSelected: Subject[] = [];
  subjects: Subject[] = [];
  subjectsIDS: string = ''

  constructor(private router: Router, private dataSaverService: DataSaverService, private messageService: MessageService) {}

  async ngOnInit(): Promise<void> {
    const teacher = this.dataSaverService.getData();

    try {
      const getScheduleSettings = await fetch("http://localhost:8080/admin/schedule/getScheduleSettings",{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      })


      const subjectsResponse = await fetch("http://localhost:8080/admin/subjectDataManager/getSubjects", {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if(getScheduleSettings.status == 200){
        getScheduleSettings.json().then(data =>{
          this.maxClassesPerWeek = data[0]
        })
      }

      if (subjectsResponse.status === 403) {
        this.router.navigate(["403"]);
        return;
      }

      if (subjectsResponse.status === 200) {
        const data = await subjectsResponse.json();
        let keys = Object.keys(data);
        for (let i = 0; i <= keys.length - 1; i++) {
          const addSubject: Subject = {
            subjectID: data[i].subjectID,
            name: data[i].subjectname,
          };
          this.subjects.push(addSubject);

          if (teacher.subjects.includes(data[i].subjectname)) {
            this.subjectsIDS = this.subjectsIDS ? this.subjectsIDS + "," + data[i].subjectID : data[i].subjectID;
            this.selectedSubjects.push(addSubject);
          }
        }
        this.subjectsTrueSelected = this.selectedSubjects;
      }
      
      const classesResponse = await fetch("http://localhost:8080/admin/classesDataManager/getSearchAllClassesRelatedToSubject/" + this.subjectsIDS
        + "/" + teacher.teacherID, {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        },
      });

      if (classesResponse.status === 403) {
        this.router.navigate(["403"]);
        return;
      }

      if (classesResponse.status === 200) {
        this;this.amountOfClasses = 0
        const data = await classesResponse.json();
        let classesIDS: String[] = []
        let keys = Object.keys(data);
        for (let i = 0; i <= keys.length - 1; i++) {
          if(data[i].isATeacherClass){
            const addTeacherClass: TeacherClass = {
              classID: data[i].classID,
              subjectID: data[i].subjectID,
              className: data[i].className,
              subjectName: data[i].subjectName,
              quantity: data[i].quantity
            };
            if(!classesIDS.includes(addTeacherClass.classID)){
              this.amountOfClasses += addTeacherClass.quantity
              this.teacherClasses.push(addTeacherClass);
              this.selectedClasses.push(addTeacherClass)
              classesIDS.push(addTeacherClass.classID + "," + addTeacherClass.subjectID);
            }
           
          }else{
            const addTeacherClass: TeacherClass = {
              classID: data[i].classID,
              subjectID: data[i].subjectID,
              className: data[i].className,
              subjectName: data[i].subjectName,
              quantity: data[i].quantity
            };
            if(!classesIDS.includes(addTeacherClass.classID)){
              this.teacherClasses.push(addTeacherClass);
              classesIDS.push(addTeacherClass.classID + "," + addTeacherClass.subjectID);
            }
          } 
          
        }
      }

    } catch (error) {
      console.error("Erro na requisição:", error);
    }

    if (teacher) {
      this.teacherID = teacher.teacherID
      this.teacherName = teacher.teacherName;
      this.teacherEmail = teacher.teacherEmail;
      this.teacherPassword = teacher.teacherPassword;
      this.teacherCPF = teacher.teacherCPF;
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  onClassChange(event:any){
    if(this.maxClassesPerWeek == 0){
      this.messageService.add({ severity: 'info', summary: 'Definir tipo de geração', detail:
        `Defina o o tipo de geração na aba (Ano Letivo) antes de atribuir professores a turmas.`});
    }else{
      let count = 0
      this.selectedClasses.forEach(c =>{
        count += c.quantity
      })
      this.amountOfClasses = count
      if(count > this.maxClassesPerWeek){
        this.messageService.add({ severity: 'info', summary: 'Limite excedido', detail:
           `O limite de aulas por semana para este professor foi excedido(${this.maxClassesPerWeek})`});
      }
    }
  }

  addClass(teacherClass: TeacherClass) {
    console.log("t")
    if (!this.selectedClasses.includes(teacherClass)) {
      this.selectedClasses.push(teacherClass);
      
      this.amountOfClasses += teacherClass.quantity
    }
  }

  removeClass(teacherClass: TeacherClass) {
    const index = this.selectedClasses.indexOf(teacherClass);
    if (index > -1) {
      this.selectedClasses.splice(index, 1);
      this.amountOfClasses -= teacherClass.quantity
    }
  }

  confirmClasses() {
    this.visible = false;
  }

  selecionarAulas() {
    this.visible = true;
  }

  openConfirmEditDialog() {
    this.confirmEditVisible = true;
  }

  closeConfirmEditDialog() {
    this.confirmEditVisible = false;
  }

  async editarProfessor() {
    if(this.maxClassesPerWeek == 0){
      this.messageService.add({ severity: 'warn', summary: 'Edição cancelada.', detail:
        `Edição cancelada, defina o tipo de geração em (Ano Letivo) para continuar.`});
      return
    }

    if(this.selectedSubjects != this.subjectsTrueSelected && this.selectedSubjects.length < this.subjectsTrueSelected.length){
      this.subjectsTrueSelected.forEach((s,i) =>{
        if(this.selectedSubjects[i].subjectID != s.subjectID){
            this.subjectsToRemove.push(this.selectedSubjects[i])
        }
      })
    }

    if(this.amountOfClasses < this.maxClassesPerWeek){
      let subjects: string[] = []
      this.selectedSubjects.forEach(t => {
        subjects.push(t.subjectID)
      })
      
      let teacherClasses: string[] = []
      let removeTeacherClasses: string[] = []
      this.selectedClasses.forEach(teacherClass => {
        let stringFormat = teacherClass.subjectID + "," + teacherClass.classID
        teacherClasses.push(stringFormat)
      })
  
      this.teacherClasses.forEach(teacherClass =>{
        if(!this.selectedClasses.includes(teacherClass)){
          let stringFormat = teacherClass.subjectID + "," + teacherClass.classID
          removeTeacherClasses.push(stringFormat)
        }
      })
      
      let teacherData = {
        teacherID: this.teacherID,
        nome: this.teacherName,
        email: this.teacherEmail,
        cpf: this.teacherCPF,
        subjects: subjects,
        teacherClasses: teacherClasses,
        removeTeacherClasses: removeTeacherClasses
      }
      
  
      try {
        const response = await fetch("http://localhost:8080/admin/teacherDataManager/updateTeacherPrimaryData", {
          method: "PATCH",
          headers: {
            "Content-type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("token")
          },
          body: JSON.stringify(teacherData)
        });
  
        if (response.ok) {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Professor editado com sucesso!' });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível editar o professor.' });
        }
  
        this.closeConfirmEditDialog();
  
      } catch (error) {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro na requisição.' });
        console.error("Erro na requisição:", error);
      }
    }else{
      this.messageService.add({ severity: 'info', summary: 'Quantidade de aulas excedida', detail: 'Por favor ajuste a quantidade de aulas para prosseguir.' });
    }
  }

  back() {
    this.router.navigate(['admin/teachers']);
  }

  async deleteTeacher() {
    try {
      const response = await fetch("http://localhost:8080/admin/teacherDataManager/deleteTeacher/" + this.teacherID, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (response.status === 200) {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Professor deletado com sucesso.' });
        this.router.navigate(['admin/teachers']);
      } else {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao deletar o professor.' });
      }

    } catch (error) {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro na requisição.' });
      console.error("Erro na requisição:", error);
    }
  }

  logout() {
    
  }
}