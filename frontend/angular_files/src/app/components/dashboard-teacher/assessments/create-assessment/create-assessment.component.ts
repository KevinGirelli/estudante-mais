import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { CalendarModule } from 'primeng/calendar';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';

interface Class {
  id: string;
  name: string;
}

@Component({
  selector: 'app-create-assessment',
  standalone: true,
  imports: [
    FormsModule,
    MultiSelectModule,
    CalendarModule,
    NgFor
  ],
  templateUrl: './create-assessment.component.html',
  styleUrl: './create-assessment.component.scss'
})
export class CreateAssessmentComponent implements OnInit {

  constructor (private router: Router,private datasaver: DataSaverService) {}

  assessmentName!: string;
  allClasses: Class[] = [];
  classesSelected: Class[] = [];
  assessmentDate: Date | undefined;

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync",{
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
        const keys = Object.keys(data);
        for(let i = 0; i <= keys.length-1; i++){
          const addClass: Class = {
            id: data[i].classID,
            name: data[i].className
          }
          this.allClasses.push(addClass)
        }
      })
    }
  }

  async registerAssessment() {
    const sendData = {
      name: this.assessmentName,
      data: this.assessmentDate,
      classID: this.classesSelected,
      teacherID: localStorage.getItem("userID")
    }
    try{
      const response = await fetch("http://localhost:8080/teacher/createNewAssessment",{
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendData)
      })
    }catch(erro){
      console.log(erro)
    }
   
  }
  
}
