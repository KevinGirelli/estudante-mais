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

  teacherClasses: TeacherClass[] = [];

  selectedClasses: TeacherClass[] = [];
  teacherID: string = '';
  teacherName: string = '';
  teacherEmail: string = '';
  teacherPassword: string = '';
  teacherCPF: string = '';
  selectedSubjects: Subject[] = [];
  subjects: Subject[] = [];
  subjectsIDS: string = ''

  constructor(private router: Router, private dataSaverService: DataSaverService, private messageService: MessageService) {}

  async ngOnInit(): Promise<void> {
    const teacher = this.dataSaverService.getData();

    try {
      const subjectsResponse = await fetch("http://localhost:8080/admin/subjectDataManager/getSubjects", {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (subjectsResponse.status === 403) {
        console.log("REDIRECT");
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
      }

      const classesResponse = await fetch("http://localhost:8080/admin/classesDataManager/getSearchAllClassesRelatedToSubject/" + this.subjectsIDS, {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        },
      });

      if (classesResponse.status === 403) {
        console.log("redirect");
        return;
      }

      if (classesResponse.status === 200) {
        const data = await classesResponse.json();
        let keys = Object.keys(data);
        for (let i = 0; i <= keys.length - 1; i++) {
          const addTeacherClass: TeacherClass = {
            classID: data[i].classID,
            subjectID: data[i].subjectID,
            className: data[i].className,
            subjectName: data[i].subjectName,
            quantity: data[i].quantity
          };
          this.teacherClasses.push(addTeacherClass);
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

  addClass(teacherClass: TeacherClass) {
    if (!this.selectedClasses.includes(teacherClass)) {
      this.selectedClasses.push(teacherClass);
    }
  }

  removeClass(teacherClass: TeacherClass) {
    const index = this.selectedClasses.indexOf(teacherClass);
    if (index > -1) {
      this.selectedClasses.splice(index, 1);
    }
  }

  confirmClasses() {
    console.log('Classes confirmadas:', this.selectedClasses);
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
    let subjects: string[] = []
    this.selectedSubjects.forEach(t => {
      subjects.push(t.subjectID)
    })
    
    let teacherClasses: string[] = []
    this.selectedClasses.forEach(teacherClass => {
      let stringFormat = teacherClass.subjectID + "," + teacherClass.classID
      teacherClasses.push(stringFormat)
    })
    
    let teacherData = {
      teacherID: this.teacherID,
      nome: this.teacherName,
      email: this.teacherEmail,
      cpf: this.teacherCPF,
      subjects: subjects,
      teacherClasses: teacherClasses
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
}