import { Component} from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';

interface ScheduleSlot {
  time: string;
  [key: string]: string | undefined;
}

@Component({
  selector: 'app-student-schedule',
  standalone: true,
  imports: [
    NgClass,
    TableModule,
    NgFor,
    HttpClientModule,
    NgIf
  ],
  templateUrl: './student-schedule.component.html',
  styleUrls: ['./student-schedule.component.scss']
})
export class StudentScheduleComponent{
  studentSchedule: ScheduleSlot[] = [];
  weekDays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];

  getClassContent(day: string, timeSlot: string): string {
    const slot = this.studentSchedule.find(slot => slot.time === timeSlot);
    return slot ? slot[day] || '' : '';
  }
}
