import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Table, TableModule } from 'primeng/table';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';

interface Assessment {
  id: string;
  assessmentName: string;
  class: string;
  subject: string;
  date: string;
  classID: string
  subjectID: string
}

@Component({
  selector: 'app-list-assessment',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
  ],
  templateUrl: './list-assessment.component.html',
  styleUrls: ['./list-assessment.component.scss']
})
export class ListAssessmentComponent implements OnInit {
  constructor (private router: Router,private datasaver: DataSaverService) {}
 
  @ViewChild('dt2') dt2!: Table;

  searchValue = '';

  assessments: Assessment[] = [];

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/assess/getAssessmentFromTeacher/" + localStorage.getItem("userID"),{
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
        console.log(data)
        const keys = Object.keys(data)
        for(let i = 0; i <= keys.length-1; i++){
          const addAsses: Assessment = {
            id: data[i].id,
            assessmentName: data[i].name,
            class: data[i].className,
            subject: data[i].subjectName,
            date: data[i].date,
            classID: data[i].classID,
            subjectID: data[i].subjectID
          }
          this.assessments.push(addAsses)
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
