import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Table, TableModule } from 'primeng/table';

interface Assessment {
  id: string;
  assessmentName: string;
  className: string;
  subject: string;
  date: string;
  classID: string;
  subjectID: string;
  teacherName: string;
  teacherID: string;
}

@Component({
  selector: 'app-my-assessments',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
  ],
  templateUrl: './my-assessments.component.html',
  styleUrl: './my-assessments.component.scss'
})
export class MyAssessmentsComponent implements OnInit {

  constructor (private router: Router) {}
  
  @ViewChild('dt2') dt2!: Table;

  searchValue = '';

  assessments: Assessment[] = [];

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/student/getAssessments/" + localStorage.getItem("userID"),{
      method: "GET",
      headers:{
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 403){
      this.router.navigate(["403"])
    }

    if(response.status == 200){
      response.json().then(data =>{
        for(let i = 0; i <= data.length -1; i++){
          let add: Assessment = {
            id: data[i].id,
            assessmentName: data[i].name,
            className: data[i].className,
            subject: data[i].subjectName,
            date: data[i].date,
            classID: data[i].classID,
            subjectID: data[i].subjectID,
            teacherName: data[i].teacherName,
            teacherID: data[i].teacherID
          }
          this.assessments.push(add);
        }
      })
    }
  }



  filterAssessment(event: any) {
    if (this.dt2) {
      this.dt2.filterGlobal(event.target.value ?? '', 'contains');
    }
  }
}