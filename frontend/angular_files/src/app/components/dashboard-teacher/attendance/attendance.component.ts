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

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/teacher/getAllClassesFromTeacher/" + localStorage.getItem("userID"), {
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
        const keys = Object.keys(data);
        for (let i = 0; i <= keys.length - 1; i++) {
          const add: Class = {
            id: data[i].classID,
            name: data[i].className
          };
          this.classes.push(add);
        }
      });
    }
  }

  async showModal() {
    const response = await fetch("http://localhost:8080/attendence/getAllStudentsFromClass/" + this.className, {
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
      classID: this.className,
      teacherID: localStorage.getItem("userID"),
      quantity: this.quantity,
      date: this.attendanceDate,
      students: students
    };

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
    } else {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao registrar presenças!' });
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
