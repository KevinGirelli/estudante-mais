import { Component } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClient, HttpClientModule } from '@angular/common/http';

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

  constructor(private http: HttpClient) {
    this.schedule = {
      '351': {
        '07:45': 'Matemática (Prof. Márcio)',
        '08:30': 'Português (Prof. Andréa)',
        '09:15': 'História (Prof. Walbert)'
      },
    };

    this.timeSlots = Array.from(new Set(Object.values(this.schedule).flatMap(day => Object.keys(day))));
    this.classes = Object.keys(this.schedule);
  }

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