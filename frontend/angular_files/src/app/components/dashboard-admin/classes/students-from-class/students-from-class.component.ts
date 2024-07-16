import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';

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
    
    fetch("http://localhost:8080/admin/classesDataManager/getAllStudentsFromClass/beb98588-6632-4d82-900e-f0c0a6f7f911",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res =>{
      if(res.status == 200){
        res.json().then(data =>{
          console.log(data)
          for(let i = 0; i <= data.length - 1; i++){
            let addStudent: Student = {
              studentID: data[i][0],
              fullname: data[i][1],
              email: data[i][2],
              cpf: data[i][3],
              age: data[i][4]
            }

            this.students.push(addStudent);
          }
        })
      }
    })
  }
}
