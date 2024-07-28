import { Component } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';

interface ScheduleSlot {
  time: string;
  [key: string]: string | undefined;
}

@Component({
  selector: 'app-teacher-schedule',
  standalone: true,
  imports: [
    NgClass,
    TableModule,
    NgFor,
    HttpClientModule,
    NgIf
  ],
  templateUrl: './teacher-schedule.component.html',
  styleUrls: ['./teacher-schedule.component.scss']
})
export class TeacherScheduleComponent {
  teacherSchedule: ScheduleSlot[] = [];
  weekDays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];

  getClassContent(day: string, timeSlot: string): string {
    const slot = this.teacherSchedule.find(slot => slot.time === timeSlot);
    return slot ? slot[day] || '' : '';
  }

}
