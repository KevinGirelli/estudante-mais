import { Component } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [
    NgClass,
    TableModule,
    NgFor,
    HttpClientModule,
    NgIf
  ],
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss']
})
export class ScheduleComponent {
  isMenuOpen = false;
  timeSlots: string[] = [];
  classes: string[] = [];
  schedule: { [key: string]: { [key: string]: string } } = {};

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  fetchSchedule() {
  }

  getClassContent(className: string, timeSlot: string): string {
    return this.schedule[className]?.[timeSlot] || '';
  }

  generateNewSchedule() {

  }

  deleteSchedule() {

  }
}