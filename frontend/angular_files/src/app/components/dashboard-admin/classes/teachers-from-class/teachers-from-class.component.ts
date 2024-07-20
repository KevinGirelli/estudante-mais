import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';

interface Subject {
  id: string
  name: string;
}

interface Teacher {
  teacherID: string
  teacherName: string;
  teacherEmail: string;
  teacherCPF: string;
  subjects: Subject[];
}

@Component({
  selector: 'app-teachers-from-class',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './teachers-from-class.component.html',
  styleUrls: ['./teachers-from-class.component.scss']
})
export class TeachersFromClassComponent implements OnInit{
  
  isMenuOpen = false;
  teachers: Teacher[] = [];
  
  constructor(private router: Router, private dataSaverService: DataSaverService) {}

  async ngOnInit(): Promise<void>{
   try{
    const response = await fetch("http://localhost:8080/admin/teacherDataManager/getAllTeacherFromClass/" + this.dataSaverService.getData(),{
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
        const keys = Object.keys(data)
       
        for(let i = 0; i <= keys.length-1; i++){
          const addTeacher: Teacher = {
            teacherID: data[i].teacherID,
            teacherName: data[i].teacherName,
            teacherEmail: data[i].teacherEmail,
            teacherCPF: data[i].cpf,
            subjects: data[i].subjects
          }
          this.teachers.push(addTeacher)
        }
      })
    }
   }catch(erro){
    console.log(erro)
   }
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  getSubjectsString(subjects: Subject[]): string {
    return subjects.map(subject => subject.name).join(', ');
  }
}
