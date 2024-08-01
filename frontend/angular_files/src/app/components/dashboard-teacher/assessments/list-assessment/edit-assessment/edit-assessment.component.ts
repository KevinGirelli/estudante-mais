import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CalendarModule } from 'primeng/calendar';
import { StepperModule } from 'primeng/stepper';
import { ListboxModule } from 'primeng/listbox';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { DataSaverService } from '../../../../../services/tempDataSaver/data-saver.service';

interface Student {
  id: number;
  name: string;
  grade: string | null;
}

@Component({
  selector: 'app-edit-assessment',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    CalendarModule,
    StepperModule,
    ListboxModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './edit-assessment.component.html',
  styleUrls: ['./edit-assessment.component.scss']
})
export class EditAssessmentComponent implements OnInit {
  assessmentId: string | null = null;
  assessmentName: string = '';
  assessmentDate: Date = new Date();
  classesSelected: string = '';
  subjectSelected: string = '';
  allClasses: { id: string, name: string }[] = [];
  allSubjects: { id: string, subjectName: string }[] = [];
  selectedStudents: Student[] = [];

  students:Student[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private messageService: MessageService
    ,private dataSavar: DataSaverService
  ) {}

  async ngOnInit(): Promise<void> {
    let assessment = this.dataSavar.getData()

    let splitDate = assessment.date.split("/")

    this.assessmentId = assessment.id
    this.assessmentName = assessment.assessmentName
    this.assessmentDate = new Date(parseInt(splitDate[2],10),parseInt(splitDate[1],10)-1,parseInt(splitDate[0],10))
    this.classesSelected = assessment.classID;
    this.subjectSelected = assessment.subjectID

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
        for(let i = 0; i <= data.subjectsIDS.length-1; i++){
          const addSubject = {
            id: data.subjectsIDS[i].split(",")[0],
            subjectName: data.subjectsIDS[i].split(",")[1]
          }
          this.allSubjects.push(addSubject)
        }
      })
    }

    if(response.status == 200){
      response.json().then(data =>{
        const keys = Object.keys(data);
        for(let i = 0; i <= keys.length-1; i++){
          const addClass = {
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

    const response3 = await fetch("http://localhost:8080/teacher/getAllStudentsFromClass/" +this.assessmentId + "," + this.classesSelected,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response3.status == 403){
      this.router.navigate(["403"])
    }

    if(response3.status == 200){
      response3.json().then(data =>{
        for(let i = 0; i <=data.length -1; i++){
          let student = {
            id: data[i].studentID,
            name: data[i].date,
            grade: data[i].gradeValue
          }
          this.students.push(student)
        }
      })
    }


  }

  async onClassChange(event: any): Promise<void> {
    this.students = [];
    const response = await fetch("http://localhost:8080/teacher/getAllStudentsFromClass/" + this.classesSelected,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 403){
      this.router.navigate(["403"])
    }

    if(response.status == 200){
      response.json().then(data =>{
        for(let i = 0; i <=data.length -1; i++){
          let student = {
            id: data[i].studentID,
            name: data[i].date,
            grade: data[i].gradeValue
          }
          this.students.push(student)
        }
      })
    }


  }

  async postGrades(): Promise<void> {
    this.students.forEach(async s =>{
      const sendData = {
        gradeValue: s.grade,
        studentID: s.id,
        assessID: this.assessmentId,
        date: this.assessmentDate,
        quarter: 0
      }

      const response = await fetch("http://localhost:8080/assess/postStudentGrade",{
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendData)
      })
  
      if(response.status == 400){
        this.messageService.add({ severity: 'error', summary: 'Erro ao postar nota', detail: 'Erro ao postar nota do aluno(a) ' + s.name })
      }
    })
    this.messageService.add({ severity: 'success', summary: 'Notas postadas.', detail: 'As notas foram postadas com sucesso.' })
  }

  async updateAssessment(): Promise<void> {
    const sendData = {
      id: this.assessmentId,
      name: this.assessmentName,
      className: "",
      subjectName: "",
      date: this.assessmentDate,
      classID: this.classesSelected,
      subjectID: this.subjectSelected,
      teacherName: "",
      teacherID: ""
    }

    const response = await fetch("http://localhost:8080/assess/updateAssessment",{
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      },
      body: JSON.stringify(sendData)
    })

    if(response.status == 200){
      console.log("Dados editados")
    }

  }
}
