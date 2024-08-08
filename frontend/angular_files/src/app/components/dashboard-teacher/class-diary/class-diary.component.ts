import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

interface DiaryEntry {
  diaryID: any
  date: Date;
  subject: any;
  content: string;
  obs: string;
  class: string;
}

interface Class {
  classID: string,
  className: string
}

interface Subject{
  subjectID: string
  subjectName: string
  classID: string,
  className: string
}

@Component({
  selector: 'app-class-diary',
  standalone: true,
  imports: [
    CommonModule,
    TabViewModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    CalendarModule,
    DropdownModule,
    FormsModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './class-diary.component.html',
  styleUrls: ['./class-diary.component.scss']
})
export class ClassDiaryComponent implements OnInit {
  constructor(private messageService: MessageService) {}

  async ngOnInit(): Promise<void>  {
    const classes = await fetch("http://localhost:8080/teacher/getAllClassesFromTeacher/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    const subjects = await fetch("http://localhost:8080/teacher/getTeacherSubject/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(subjects.status == 200){
      subjects.json().then(data =>{
        for(let i = 0; i <= data.subjectsIDS.length-1;i++){
          let split = data.subjectsIDS[i].split(",")
          let addSubject: Subject = {
            subjectID: split[0],
            subjectName: split[1],
            classID: split[3],
            className: split[2]
          }
          this.subjectOptions.push(addSubject)
        }
      })
    }

    if(classes.status == 200){
      classes.json().then(data =>{
        for(let i = 0; i <= data.length-1; i++){
          let addClass: Class = {
            classID: data[i].classID,
            className: data[i].className
          }
          this.classOptions.push(addClass)
        }
      })
    }
  }

  classOptions: Class[] = [];

  subjectOptions: Subject[] = [];
  subjectOptionFilter: Subject[] = []

  selectedClass: any | null = null;
  classDiary: DiaryEntry[] = [];

  displayAddDiaryModal = false;
  displayEditDiaryModal = false;
  newDiary: DiaryEntry = { diaryID: '', date: new Date(), subject: '', content: '', obs: '', class: '' };
  selectedDiary: DiaryEntry = {diaryID: '', date: new Date(), subject: '', content: '', obs: '', class: '' };

  get filteredDiaryEntries(): DiaryEntry[] {
    return this.classDiary.filter(entry => entry.class === this.selectedClass.className);
  }

  async showAddDiaryModal() {
    if (this.selectedClass) {
      this.newDiary = { diaryID: '', date: new Date(), subject: '', content: '', obs: '', class: this.selectedClass.className};
      this.displayAddDiaryModal = true;

      this.subjectOptionFilter = []
      this.subjectOptions.forEach(s =>{
        if(s.className == this.selectedClass.className) this.subjectOptionFilter.push(s)
      })
    }
  }

  async addDiary() {
    if (this.newDiary.date && this.newDiary.subject && this.newDiary.content && this.newDiary.obs) {
      let sendData = {
        subjectID: this.newDiary.subject.subjectID,
        subjectName: "",
        dayContent: this.newDiary.content,
        observations: this.newDiary.obs,
        registryDate: this.newDiary.date,
        classID: this.selectedClass.classID,
        className: "",
        teacherID: localStorage.getItem("userID")
      }

      let response = await fetch("http://localhost:8080/classDiary/registerNewDay",{
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendData)
      })

      if(response.status == 200){
        let data = await response.text();
        this.newDiary.diaryID = data
        this.classDiary.push(this.newDiary);
        this.displayAddDiaryModal = false;
      }
      if(response.status == 400){
        this.messageService.add({ severity: 'info', summary: 'Registro encontrado', detail: 
          'Já existe um diário neste dia para a turma ' + this.selectedClass.className + ' na disciplina de ' + this.newDiary.subject.subjectName
         });
      }
      
    }
  }

  async onClassesChange(){
    this.classDiary = []
    this.subjectOptionFilter = []

    this.subjectOptions.forEach(s =>{
      if(s.className == this.selectedClass.className){
        this.subjectOptionFilter.push(s)
      }
    })

    const response = await fetch("http://localhost:8080/classDiary/getClassDiary/"
      + localStorage.getItem("userID") + "/" + this.selectedClass.classID
    ,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      response.json().then(data =>{
        for(let i = 0; i <= data.length-1;i++){
          const [day, month, year] = data[i].registryDate.split('/').map((part: string) => parseInt(part, 10));
          let addSubject: Subject = {
            subjectID: data[i].subjectID,
            subjectName: data[i].subjectName,
            classID: data[i].classID,
            className: data[i].className
          }

          let add: DiaryEntry = {
            diaryID: data[i].diaryID,
            date: new Date(year,month-1,day),
            subject: addSubject,
            content: data[i].dayContent,
            obs: data[i].observations,
            class: data[i].className
          }
          this.classDiary.push(add)
        }
      })
    }
  }

  editDiary(diary: DiaryEntry) {
    this.selectedDiary = { ...diary };
    this.displayEditDiaryModal = true;
  }

  async updateDiary() {
    let dataToUpdate = {
      diaryID: this.selectedDiary.diaryID,
      subjectID: this.selectedDiary.subject.subjectID,
      subjectName: "",
      dayContent: this.selectedDiary.content,
      observations: this.selectedDiary.obs,
      registryDate: this.selectedDiary.date,
      classID: this.selectedDiary.subject.classID,
      className: "",
      teacherID: localStorage.getItem("userID")
    }

    console.log(this.selectedDiary.subject.classID)

    let response = await fetch("http://localhost:8080/classDiary/editClassDiary",{
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      },
      body: JSON.stringify(dataToUpdate)
    })
    
    if(response.status == 200){
      const index = this.classDiary.forEach((c,i) =>{
        if(c.diaryID == this.selectedDiary.diaryID) this.classDiary[i] = this.selectedDiary
      })
      this.displayEditDiaryModal = false;
    }

    if(response.status == 400){
      this.messageService.add({ severity: 'info', summary: 'Registro encontrado', detail: 
        'Já existe um diário neste dia para a turma ' + this.selectedClass.className + ' na disciplina de ' + this.selectedDiary.subject.subjectName
       });
    }
  }

  async deleteDiary(diary: DiaryEntry) {
    const response = await fetch("http://localhost:8080/classDiary/deleteClassDiary/" + diary.diaryID,{
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      this.classDiary = this.classDiary.filter(d => d !== diary);
    }
  }
}
