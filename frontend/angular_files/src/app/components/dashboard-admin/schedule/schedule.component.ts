import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

interface ClassSchedule {
  time: string;
  subject: string;
}

interface ClassData {
  className: string;
  classSchedule: ClassSchedule[];
}

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
  timeSlots: { time: string }[] = [];
  classes: string[] = [];
  schedule: { [key: string]: { [key: string]: string } } = {};

  constructor(private router: Router) {
    this.timeSlots = Array.from(new Set(Object.values(this.schedule).flatMap(day => Object.keys(day))))
                          .map(time => ({ time }));
    this.classes = Object.keys(this.schedule);
  }

  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/admin/schedule/getSchedule",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    if(response.status == 403){
      this.router.navigate(["403"]);
    }

    if(response.status == 200){
      response.json().then(data =>{
        console.log()
        for(let i = 0; i < data.classes.length; i++){
          for(let j = 0; j < data.classes[i].classSchedule.length; j++){
            const classSchedule = data.classes[i].classSchedule[j];
            if(!this.schedule[data.classes[i].className]){
              this.schedule[data.classes[i].classSchedule[i]] = {};
            }
            this.schedule[data.classes[i].className][classSchedule.time] = classSchedule.subject;
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
