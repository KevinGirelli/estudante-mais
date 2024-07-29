import { Component, OnInit, ViewChild } from '@angular/core';
import { NgClass, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { Router } from '@angular/router';
import { DataSaverService } from '../../../services/tempDataSaver/data-saver.service';

interface Teacher {
  teacherID: string;
  teacherName: string;
  teacherEmail: string;
  teacherPassword: string;
  teacherCPF: string;
  subjects: string[];
  teacherClasses: string[];
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

  constructor(private router: Router, private dataSaverService: DataSaverService) {}

  ngOnInit(): void {
    fetch("http://localhost:8080/admin/teacherDataManager/getAllTeachers", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if (res.status == 403) {
        console.log("REDIRECT");
        this.router.navigate(["403"])
      }

      if (res.status == 200) {
        res.json().then(data => {
          const keys = Object.keys(data);

          keys.forEach(key => {
            let addTeacher: Teacher = {
              teacherID: data[key].teacherID,
              teacherName: data[key].teacherName,
              teacherEmail: data[key].teacherEmail,
              teacherPassword: data[key].teacherPassword,
              teacherCPF: data[key].cpf,
              subjects: data[key].subjects,
              teacherClasses: data[key].teacherClasses
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
    this.dataSaverService.setData(teacher);
    this.router.navigate(['/admin/teachers', teacher.teacherID]);
  }

  logout() {
    
  }
}
