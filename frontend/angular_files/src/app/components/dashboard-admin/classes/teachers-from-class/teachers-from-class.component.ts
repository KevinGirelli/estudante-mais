import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';

interface Subject {
  id: string
  name: string;
}

interface Teacher {
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
  
  constructor(private router: Router) {}

  ngOnInit(): void {
    fetch("http://localhost:8080/admin/subjectDataManager/getSubjects",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
        this.router.navigate(["403"])
      }
    })
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  getSubjectsString(subjects: Subject[]): string {
    return subjects.map(subject => subject.name).join(', ');
  }
}
