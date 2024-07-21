import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { ListboxModule } from 'primeng/listbox';
import { NgClass, NgFor, NgIf, NgStyle } from '@angular/common';
import { SelectItem } from 'primeng/api';

interface Student {
  id: number;
  name: string;
  attendance: string;
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
    NgClass
  ],
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent {
  visible: boolean = false;
  students: Student[] = [
    { id: 1, name: 'Kévin', attendance: 'Presente' },
    { id: 2, name: 'Zaymã', attendance: 'Ausente' },
    { id: 3, name: 'Leonardo', attendance: 'Justificado' }
  ];
  selectedStudents: Student[] = [];
  classes: SelectItem[] = [
    { label: '351', value: '351' },
    { label: '352', value: '352' },
    { label: '353', value: '353' }
  ];
  className: string = '';
  quantity: number = 0;
  attendanceDate: Date = new Date();

  showModal() {
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

  registerAttendance() {
    console.log('Attendance registered for:', this.selectedStudents);
    this.visible = false;
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
    this.visible = false
  }
}