import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

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
export class ScheduleComponent implements OnInit {
  isMenuOpen = false;
  timeSlots: string[] = [];
  classes: string[] = [];
  schedule: { [key: string]: { [key: string]: string } } = {};

  constructor(private http: HttpClient, private router: Router) {
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

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/admin/schedule/getSchedule",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 403){
      this.router.navigate(["403"])
    }

    if(response.status == 200){
      response.json().then(data =>{
        for(let i = 0; i <= data.classes.length-1; i++){
            for(let c = 0; c <= data.classes[i].classSchedule.length-1; c++){
              
            }
        }
      })
    }
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