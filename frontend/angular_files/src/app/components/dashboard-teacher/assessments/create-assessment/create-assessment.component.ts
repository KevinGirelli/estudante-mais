import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { CalendarModule } from 'primeng/calendar';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

interface Class {
  id: string;
  name: string;
}

interface subject {
  id: string,
  subjectName: string
}

@Component({
  selector: 'app-create-assessment',
  standalone: true,
  imports: [
    FormsModule,
    MultiSelectModule,
    CalendarModule,
    NgFor,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './create-assessment.component.html',
  styleUrl: './create-assessment.component.scss'
})
export class CreateAssessmentComponent implements OnInit {

  constructor(private router: Router, private datasaver: DataSaverService, private messageService: MessageService) {}

  assessmentName!: string;
  allClasses: Class[] = [];
  classesSelected: Class[] = [];
  assessmentDate: Date | undefined;

  subjectSelected: subject[] = [];
  allSubjects: subject[] = [];

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/teacher/getAllClassesFromTeacher/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    const response2 = await fetch("http://localhost:8080/teacher/getTeacherSubject/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 403){
      this.router.navigate(["403"])
    }

    if(response2.status == 403){
      this.router.navigate(["403"])
    }

    if(response2.status == 200){
      response2.json().then(data => {
        let alreadyInSubjectList: String[] = []
        for(let i = 0; i <= data.subjectsIDS.length-1; i++){
          const addSubject: subject = {
            id: data.subjectsIDS[i].split(",")[0],
            subjectName: data.subjectsIDS[i].split(",")[1]
          }
          if(!alreadyInSubjectList.includes(addSubject.id)){
            this.allSubjects.push(addSubject)
          }
          alreadyInSubjectList.push(addSubject.id)
        }
      })
    }

    if(response.status == 200){
      response.json().then(data =>{
        const keys = Object.keys(data);
        for(let i = 0; i <= keys.length-1; i++){
          const addClass: Class = {
            id: data[i].classID,
            name: data[i].className
          }
          let alreadExist = false
          this.allClasses.forEach(c =>{
            if(c.id == addClass.id){
              alreadExist = true
            }
          })
          if(alreadExist == false){
            this.allClasses.push(addClass)
          }
        }
      })
    }
  }

  async registerAssessment() {
    const sendData = {
      name: this.assessmentName,
      data: this.assessmentDate,
      classID: this.classesSelected,
      teacherID: localStorage.getItem("userID"),
      subjectID: this.subjectSelected
    }
    try {
      const response = await fetch("http://localhost:8080/assess/createNewAssessment", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendData)
      });

      if (response.ok) {
        this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Avaliação registrada com sucesso!' });
        this.clearForm();
      } else {
        this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar avaliação!' });
      }
    } catch (erro) {
      console.log(erro);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar avaliação!' });
    }
  }

  clearForm() {
    this.assessmentName = '';
    this.assessmentDate = undefined;
    this.classesSelected = [];
    this.subjectSelected = [];
  }
}
