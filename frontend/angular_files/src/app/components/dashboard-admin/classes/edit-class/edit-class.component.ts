import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ListboxModule } from 'primeng/listbox';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

interface Subject {
  classSubjectID: string
  subjectID: string;
  name: string;
  quantity: number;
}

@Component({
  selector: 'app-edit-class',
  standalone: true,
  imports: [
    FormsModule, 
    NgClass, 
    ToastModule, 
    NgFor, 
    NgIf,
    DialogModule,
    ListboxModule
    
  ],
  providers: [MessageService],
  templateUrl: './edit-class.component.html',
  styleUrls: ['./edit-class.component.scss']
})
export class EditClassComponent implements OnInit {
  isMenuOpen = false;
  confirmEditVisible = false;
  visibleSubjects = false;
  cadastroMateriasVisible = false;

  classID: any = ""
  className = '';
  gradeType = '';
  gradeNumber: number[] = [];
  classPeriod = '';
  periods: string[] = [];
  gradeNumbers: number[] = [];
  subjects: Subject[] = [];
  subjectsClasses: Subject[] = [];
  subjectName = '';
  allPeriods: string[] = ["Matutino","Vespertino","Integral","Noturno", "Matutino + Noturno", "Vespertino + Noturno", "Integral + Noturno"];
  totalClasses: number = 0
  maxClasses: number = 0

  constructor(private messageService: MessageService, private route: ActivatedRoute,private router: Router) {}

  async ngOnInit(): Promise<void> {
    this.classID = this.route.snapshot.paramMap.get('editClass');
    
    let getSchoolPeriodType = await fetch("http://localhost:8080/admin/classesDataManager/getPeriodType",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    let getClasses = await fetch("http://localhost:8080/admin/classesDataManager/getClass/" + this.classID,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    let getClassSubject = await fetch("http://localhost:8080/admin/classesDataManager/getClassSubjects/" + this.classID,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(getClassSubject.status == 200){
      getClassSubject.json().then(data =>{
        let ids = data.subjectsIDS

        for(let i = 0; i <= ids.length-1; i++){
          let split = ids[i].split(",")

          let subject: Subject = {
            classSubjectID: split[0],
            subjectID: split[2],
            name: split[4],
            quantity: split[5]
          }
          this.totalClasses += parseInt(split[5])
          
          this.subjects.push(subject)
        }
      })
    }

    if(getSchoolPeriodType.status == 200){
      getSchoolPeriodType.text().then(data =>{
        let toInt = parseInt(data)
        
        if(toInt > 3){
            if(toInt == 4){
              this.periods.push(this.allPeriods[0])
              this.periods.push(this.allPeriods[3])
            }

            if(toInt == 5){
              this.periods.push(this.allPeriods[1])
              this.periods.push(this.allPeriods[3])
            }

            if(toInt == 6){
              this.periods.push(this.allPeriods[0])
              this.periods.push(this.allPeriods[1])
              this.periods.push(this.allPeriods[3])
            }
        }else{
          if(toInt == 0){
            this.periods.push(this.allPeriods[0])
          }

          if(toInt == 1){
            this.periods.push(this.allPeriods[1])
          }

          if(toInt == 2){
            this.periods.push(this.allPeriods[0])
            this.periods.push(this.allPeriods[1])
            this.periods.push(this.allPeriods[2])
          }

          if(toInt == 3){
            this.periods.push(this.allPeriods[3])
          }
        }
      })
    }

    if(getClasses.status == 200){
      getClasses.json().then(data =>{
        this.classPeriod = data.type
        this.className = data.className
        this.gradeType = data.gradeType
        this.gradeNumber = data.gradeNumber
        this.updateGradeNumbers()
        
        let intSchoolType = this.allPeriods.indexOf(this.classPeriod)
        
        if(intSchoolType == 0 || intSchoolType == 1 || intSchoolType == 3 || intSchoolType == 4 || intSchoolType == 4 || intSchoolType == 5){
          this.maxClasses = 25
        }

        if(intSchoolType == 2 ){
          this.maxClasses = 50
        }
        
      })
    }

  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  openConfirmEditDialog() {
    this.confirmEditVisible = true;
  }

  closeConfirmEditDialog() {
    this.confirmEditVisible = false;
  }

  selecionarMateria() {
    this.visibleSubjects = true;
  }

  confirmarMaterias() {
    this.visibleSubjects = false;
    this.messageService.add({ severity: 'success', summary: 'Matérias Selecionadas', detail: 'As matérias foram confirmadas com sucesso.' });
  }

  cadastrarMateria() {
  }

  abrirCadastroMateria() {
    this.cadastroMateriasVisible = true;
  }

  async editarClasse() {
    
    let intSchoolType = this.allPeriods.indexOf(this.classPeriod)
        
    if(intSchoolType == 0 || intSchoolType == 1 || intSchoolType == 3 || intSchoolType == 4 || intSchoolType == 4 || intSchoolType == 5){
        
    }

    if(intSchoolType != 2 && this.totalClasses > 25){
      this.messageService.add({ severity: 'warn', summary: 'Edição cancelada', detail: `A quantidade máxima de aulas semanais do periodo selecionado (${this.classPeriod}) é menor do que a quantidade selecionada (${this.totalClasses}). Diminua a quantidade para até 25 aulas para prosseguir`});
      return
    }

    this.confirmEditVisible = true;

    let mainClassData = {
      classID: this.classID,
      className: this.className,
      gradeNumber: this.gradeNumber,
      gradeType: this.gradeType,
      teacher: "",
      type: this.classPeriod
    }

    let editSubjects: String[] = []
    this.subjects.forEach(s =>{
      editSubjects.push(s.classSubjectID + "," + s.quantity)
    })

    let newData = {
      classes: mainClassData,
      subjectDTO: {
        subjectsIDS: editSubjects
      }
    }

    let updateData = await fetch("http://localhost:8080/admin/classesDataManager/updateClassData/" + this.classID,{
      method: "PATCH",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
        "Content-type": "application/json"
      },
      body: JSON.stringify(newData)
    })

    if(updateData.status == 200){
      this.messageService.add({ severity: 'success', summary: 'Turma Editada', detail: 'A turma foi editada com sucesso.' });
    }else{
      this.messageService.add({ severity: 'erro', summary: 'Ocorreu um problema ao editar turma', detail: 'Edição de turma cancelada.' });
    }
    
  }

  updateGradeNumbers() {
    switch (this.gradeType) {
      case 'Fundamental 1':
        this.gradeNumbers = [1, 2, 3, 4, 5];
        break;
      case 'Fundamental 2':
        this.gradeNumbers = [6, 7, 8, 9];
        break;
      case 'Ensino Médio':
        this.gradeNumbers = [1, 2, 3];
        break;
      default:
        this.gradeNumbers = [];
    }
  }

  increaseQuantity(subject: Subject) {
    if(this.totalClasses+1 <= this.maxClasses){
      subject.quantity++;
      this.totalClasses++;
    }else{
      if(this.totalClasses == 50){
        this.messageService.add({ severity: 'info', summary: 'Adição Bloqueada', detail: 'Limite de aulas por semana atingido (50)'});
      }else{
        this.messageService.add({ severity: 'info', summary: 'Adição Bloqueada', detail: 'O periodo selecionado permite apenas um limite de ' + this.maxClasses + " aulas por semana, modifique o periodo para integral caso seja necessário um maior limite" });
      }
    }
  }

  decreaseQuantity(subject: Subject) {
    if (subject.quantity > 0) {
      subject.quantity--;
      this.totalClasses--;
    }
  }

  logout() {
    
  }

  async deleteClass()  {
    let response = await fetch("http://localhost:8080/admin/classesDataManager/deleteClass/" + this.classID,{
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })

    if(response.status == 400){
      this.messageService.add({ severity: 'info', summary: 'Remoção não autorizada', detail: 'Esta turma ainda possui alunos pendentes, remova-os antes de executar esta ação.' });
    }

    if(response.status == 200){
      this.router.navigate(['admin/classes']);
    }

  }

  back() {
    this.router.navigate(['admin/classes']);
  }
}
