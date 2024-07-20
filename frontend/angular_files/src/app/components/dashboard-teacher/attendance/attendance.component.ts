import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [
    FormsModule,
    CalendarModule,
    NgFor
  ],
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent {
  className: string | undefined;
  attendanceDate: Date | undefined;
  classes: string[] = ['Turma A', 'Turma B', 'Turma C'];
  quantity: number = 0;

  registerAttendance() {
  }

  incrementQuantity() {
    this.quantity++;
  }

  decrementQuantity() {
    if (this.quantity > 0) {
      this.quantity--;
    }
  }
}
