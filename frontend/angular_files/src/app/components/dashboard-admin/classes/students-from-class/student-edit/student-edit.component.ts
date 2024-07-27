import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { NgClass, NgIf, NgFor } from '@angular/common';
import { DataSaverService } from '../../../../../services/tempDataSaver/data-saver.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';

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
    NgFor,
    NgIf,
    ToastModule,
    DialogModule
  ],
  providers: [MessageService],
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.scss']
})
export class StudentEditComponent implements OnInit {
  isMenuOpen = false;
  confirmEditVisible: boolean = false;
  confirmDeleteVisible: boolean = false;

  studentID: string = '';
  Fullname: string = '';
  email: string = '';
  password: string = '';
  cpf: string = '';
  age: string = '';
  classesSelected: string = '';
  allClasses: Class[] = [];

  constructor(private router: Router, private dataSaverService: DataSaverService, private messageService: MessageService) {}

  ngOnInit(): void {
    const student = this.dataSaverService.getData()[0];

    if (student) {
      this.studentID = student.studentID;
      this.Fullname = student.fullname;
      this.email = student.email;
      this.password = student.password;
      this.cpf = student.cpf;
      this.age = new Date(student.age).toISOString().split('T')[0];
      this.classesSelected = student.classID;
    }

    fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if (res.status === 403) {
        console.log("redirect");
        this.router.navigate(["403"]);
      }

      if (res.status === 200) {
        res.json().then(data => {
          const keys = Object.keys(data);

          for (let i = 0; i <= keys.length - 1; i++) {
            const addClass: Class = {
              id: data[i].classID,
              name: data[i].className
            };
            this.allClasses.push(addClass);
          }
        });
      }
    });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  openConfirmEditDialog() {
    this.confirmEditVisible = true;
  }

  closeConfirmEditDialog() {
    this.confirmEditVisible = false;
  }

  async editarAluno() {
    const sendStudentData = {
      studentID: this.studentID,
      fullname: this.Fullname,
      email: this.email,
      cpf: this.cpf,
      age: this.age,
      classID: this.classesSelected
    };

    try {
      const response = await fetch("http://localhost:8080/admin/studentDataManager/updateStudentPrimaryData", {
        method: "PATCH",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendStudentData)
      });

      if (response.ok) {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Aluno editado com sucesso!' });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível editar o aluno.' });
      }

      this.closeConfirmEditDialog();

    } catch (error) {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro na requisição.' });
      console.error("Erro na requisição:", error);
    }
  }

  openConfirmDeleteDialog() {
    this.confirmDeleteVisible = true;
  }

  closeConfirmDeleteDialog() {
    this.confirmDeleteVisible = false;
  }

  async deleteStudent() {
    try {
      const response = await fetch(`http://localhost:8080/admin/studentDataManager/deleteStudent/${this.studentID}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (response.ok) {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Aluno deletado com sucesso!' });
        this.router.navigate(['admin/students']);
      } else {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível deletar o aluno.' });
      }

      this.closeConfirmDeleteDialog();

    } catch (error) {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro na requisição.' });
      console.error("Erro na requisição:", error);
    }
  }

  back() {
    this.router.navigate(['admin/students/']);
  }

  logout() {
    
  }
}
