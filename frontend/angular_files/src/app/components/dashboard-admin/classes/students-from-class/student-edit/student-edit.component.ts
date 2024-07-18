import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { NgClass } from '@angular/common';
import { NgFor } from '@angular/common';
import { DataSaverService } from '../../../../../services/tempDataSaver/data-saver.service';

interface Class {
  id: string;
  name: string;
}

@Component({
  selector: 'app-student-edit',
  standalone: true,
  imports: [
    FormsModule,
    InputMaskModule,
    NgClass,
    NgFor
  ],
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.scss']
})
export class StudentEditComponent implements OnInit {
  isMenuOpen = false;

  studentID: string = ''
  Fullname: string = '';
  email: string = '';
  password: string = '';
  cpf: string = '';
  age: string = '';
  classesSelected: string = '';
  allClasses: Class[] = [];

  constructor(private dataSaverService: DataSaverService) {}

  ngOnInit(): void {
    const student = this.dataSaverService.getData()[0];

    if (student) {
      this.studentID = student.studentID
      this.Fullname = student.fullname;
      this.email = student.email;
      this.password = student.password;
      this.cpf = student.cpf;
      this.age = new Date(student.age).toISOString().split('T')[0];
      this.classesSelected = student.classID;
    }

    // Suponha que você já tenha um serviço para buscar todas as classes
    fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res =>{
      if(res.status == 403){
        console.log("redirect")
      }

      if(res.status == 200){
        res.json().then(data =>{
          const keys = Object.keys(data)

          for(let i = 0; i <= keys.length-1; i++){
            const addClass: Class = {
              id: data[i].classID,
              name: data[i].className
            }
            this.allClasses.push(addClass)
          }
        });
      }
    });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  editarAluno() {
    let sendStudentData = {
      studentID: this.studentID,
      fullname: this.Fullname,
      email: this.email,
      cpf: this.cpf,
      age: this.age,
      classID: this.classesSelected
    }
    
    fetch("http://localhost:8080/admin/studentDataManager/updateStudentPrimaryData",{
      method: "PATCH",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      },
      body: JSON.stringify(sendStudentData)
    })
  }
}
