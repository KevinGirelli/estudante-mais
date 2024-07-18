import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';
import { Router } from '@angular/router';

interface Student {
  studentID: string
  fullname: string;
  email: string;
  cpf: string;
  age: Date;
}

@Component({
  selector: 'app-students-from-class',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './students-from-class.component.html',
  styleUrls: ['./students-from-class.component.scss']
})
export class StudentsFromClassComponent implements OnInit {

  constructor(private router: Router,private datasaver: DataSaverService) {}
  
  isMenuOpen = false;
  students: Student[] = [];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit() {
    fetch("http://localhost:8080/auth/verifyAdminToken",{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
      }
    })
    
    fetch("http://localhost:8080/admin/classesDataManager/getAllStudentsFromClass/" + this.datasaver.getData(),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res =>{
      if(res.status == 200){
        res.json().then(data =>{
          const keys = Object.keys(data)
          
          for(let i = 0; i <= keys.length-1; i++){
            const addClass: Student = {
              studentID: data[i].studentID,
              fullname: data[i].student_fullname,
              email: data[i].student_email,
              cpf: data[i].studentcpf,
              age: data[i].student_age
            }
            this.students.push(addClass)
          }
        })
      }
    })
  }

  studentEdit(student: Student) {
    this.datasaver.setData(student);
    this.router.navigate(['admin/class/students/classStudent', student.fullname]);
  }
}
