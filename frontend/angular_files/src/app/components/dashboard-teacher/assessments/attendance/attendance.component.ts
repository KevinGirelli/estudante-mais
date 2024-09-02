import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { ListboxModule } from 'primeng/listbox';
import { NgClass, NgFor, NgIf, NgStyle } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

interface Student {
  id: number;
  name: string;
  attendance: string;
}

interface Class {
  id: string;
  name: string;
  className: string
  classID: string
  subjectName: string;
}

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [
    FormsModule,
    CalendarModule,
    DialogModule,
    ButtonModule,
    ListboxModule,
    NgFor,
    NgIf,
    NgStyle,
    NgClass,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent implements OnInit {

  constructor(private router: Router, private messageService: MessageService) {}

  visible: boolean = false;
  students: Student[] = [];

  selectedStudents: Student[] = [];
  classes: Class[] = [];
  className: string = '';
  quantity: number = 0;
  attendanceDate: Date = new Date();
  isDisabled: boolean = true


  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/teacher/getTeacherSubject/" + localStorage.getItem("userID"), {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    if (response.status == 403) {
      this.router.navigate(["403"]);
    }

    if (response.status == 200) {
      response.json().then(data => {
        for(let i = 0; i <= data.subjectsIDS.length-1; i++){
          let dataSplit = data.subjectsIDS[i].split(",")
          let addClass: Class = {
            id: dataSplit[0],
            name: dataSplit[2] + "-" + dataSplit[1],
            classID: dataSplit[0] + "," + dataSplit[3],
            className: dataSplit[2],
            subjectName: dataSplit[1]
          }
          this.classes.push(addClass);
        }
      });
    }
  }

  async showModal() {
    const response = await fetch("http://localhost:8080/attendence/getAllStudentsFromClass/" + this.className.split(",")[1], {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    if (response.status == 200) {
      this.students = []
      response.json().then(data => {
        const keys = Object.keys(data);
        for (let i = 0; i <= keys.length - 1; i++) {
          const add: Student = {
            id: data[i].studentID,
            name: data[i].student_fullname,
            attendance: 'Presente'
          };

          let alreadyHasStudent = false;
          this.students.forEach(student => {
            if (student.id == add.id) {
              alreadyHasStudent = true;
            }
          });

          if (alreadyHasStudent == false) {
            this.students.push(add);
          }
        }
      });
    }

    this.visible = true;
  }

  incrementQuantity() {
    this.quantity++;
  }

  decrementQuantity() {
    if (this.quantity > 0) {
      this.quantity--;
    }
  }

  async registerAttendance() {
    const students: string[] = [];
    this.students.forEach(s => {
      students.push(s.id + "," + s.attendance.toUpperCase());
    });

    const sendData = {
      classID: this.className.split(",")[1],
      teacherID: localStorage.getItem("userID"),
      quantity: this.quantity,
      date: this.attendanceDate,
      students: students,
      subjectID: this.className.split(",")[0]
    };

    if(sendData.students.length == 0){
      this.messageService.add({ severity: 'info', summary: 'Alunos insuficientes', detail: 'Nenhum aluno encontrado para está turma, a chamada não foi registrada.'});
    }else{
      const response = await fetch("http://localhost:8080/attendence/registryAttendence", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(sendData)
      });
  
      if (response.status == 200) {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Presenças registradas com sucesso!' });
      } else if(response.status == 400) {
        this.messageService.add({ severity: 'info', summary: 'Chamada já registrada.', detail: 'A chamada para está turma ja foi registrada hoje.' + 
          " Contate o administrador da instituição caso houve algum tipo de engano."
        });
      }
    }
  }

  getButtonLabel(attendance: string): string {
    if (attendance === 'Presente') return 'Presente';
    if (attendance === 'Ausente') return 'Ausente';
    if (attendance === 'Justificado') return 'Justificado';
    return '';
  }

  getButtonColor(attendance: string): string {
    if (attendance === 'Presente') return '#66BB6A';
    if (attendance === 'Ausente') return '#EF5350';
    if (attendance === 'Justificado') return '#FBC02D';
    return '';
  }

  getClass(attendance: string): string {
    if (attendance === 'Presente') return 'btn-attendance pi pi-check-circle';
    if (attendance === 'Ausente') return 'btn-attendance pi pi-times-circle';
    if (attendance === 'Justificado') return 'btn-attendance pi pi-info-circle';
    return '';
  }

  toggleAttendance(student: Student) {
    if (student.attendance === 'Presente') {
      student.attendance = 'Ausente';
    } else if (student.attendance === 'Ausente') {
      student.attendance = 'Justificado';
    } else {
      student.attendance = 'Presente';
    }
  }

  saveAttendance() {
    this.visible = false;
  }
}
