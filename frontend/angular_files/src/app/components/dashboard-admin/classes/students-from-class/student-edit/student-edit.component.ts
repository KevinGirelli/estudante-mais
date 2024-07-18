import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { NgClass } from '@angular/common';
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
    NgClass
  ],
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.scss']
})
export class StudentEditComponent implements OnInit {
  isMenuOpen = false;

  Fullname: string = '';
  email: string = '';
  password: string = '';
  cpf: string = '';
  age: string = '';
  classesSelected: string = '';
  allClasses: Class[] = [];

  constructor(private dataSaverService: DataSaverService) {}

  ngOnInit(): void {
    const student = this.dataSaverService.getData();

    if (student) {
      this.Fullname = student.fullname;
      this.email = student.email;
      this.password = student.password;
      this.cpf = student.cpf;
      this.age = new Date(student.age).toISOString().split('T')[0];
      this.classesSelected = student.classID;
    }

    // Suponha que você já tenha um serviço para buscar todas as classes
    fetch("http://localhost:8080/admin/classesDataManager/getAllClasses",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res =>{
      if(res.status == 200){
        res.json().then(data =>{
          this.allClasses = data.map((classItem: any) => ({
            id: classItem.id,
            name: classItem.name
          }));
        });
      }
    });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  editarAluno() {
    // Implementar a lógica para editar o aluno
  }
}
