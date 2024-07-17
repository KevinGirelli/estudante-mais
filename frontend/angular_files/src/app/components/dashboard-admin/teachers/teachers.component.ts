import { Component, OnInit, ViewChild } from '@angular/core';
import { NgClass, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { Router } from '@angular/router';

interface Teacher {
  teacherID: string;
  teacherName: string;
  teacherEmail: string;
  teacherCPF: string;
  subjects: string[];
}

@Component({
  selector: 'app-teachers',
  standalone: true,
  imports: [
    NgClass,
    CommonModule,
    TableModule,
    ButtonModule,
    FormsModule,
  ],
  providers: [FormsModule],
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements OnInit  {
  @ViewChild('dt2') dt2!: Table;

  isMenuOpen = false;
  teachers: Teacher[] = [];
  searchValue = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    fetch("http://localhost:8080/admin/teacherDataManager/getAllTeachers", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if (res.status == 403) {
        console.log("REDIRECT");
      }

      if (res.status == 200) {
        res.json().then(data => {
          const keys = Object.keys(data);

          keys.forEach(key => {
            let addTeacher: Teacher = {
              teacherID: data[key].teacherID,
              teacherName: data[key].teacherName,
              teacherEmail: data[key].teacherEmail,
              teacherCPF: data[key].cpf,
              subjects: data[key].subjects
            };
            this.teachers.push(addTeacher);
          });
        });
      }
    });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  filterTeachers(event: any) {
    if (this.dt2) {
      this.dt2.filterGlobal(event.target.value ?? '', 'contains');
    }
  }

  teacherEdit(teacher: Teacher) {
    this.router.navigate(['/admin/teachers', teacher.teacherName]);
  }
}
