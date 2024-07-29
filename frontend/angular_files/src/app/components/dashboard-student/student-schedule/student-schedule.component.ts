import { Component, OnInit} from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

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
    NgIf,
    ToastModule
  ],
  templateUrl: './student-schedule.component.html',
  styleUrls: ['./student-schedule.component.scss'],
  providers: [MessageService]
})
export class StudentScheduleComponent implements OnInit{
  constructor (private messageService: MessageService) {}
  
  
  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/student/viewSchedule/" + localStorage.getItem("classID"),{
      method: "POST",
      headers:{
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })
   

    if(response.status == 200){
      response.json().then(data =>{
        if(data.hours.length == 60){
          let monday = 1
          let tuesday = 13
          let wednesday = 25
          let thursday = 37
          let friday = 49

          for(let i = 1; i <= 11; i++){
            let add: ScheduleSlot ={
              time: data.hours[i],
              monday: data.classes[0].classSchedule[i],
              tuesday: data.classes[0].classSchedule[tuesday],
              wednesday: data.classes[0].classSchedule[wednesday],
              thursday: data.classes[0].classSchedule[thursday],
              friday: data.classes[0].classSchedule[friday]
            }
            this.studentSchedule.push(add)
            monday++
            tuesday++
            wednesday++
            thursday++
            friday++
          }
        }

        if(data.hours.length == 30){
          let monday = 1
          let tuesday = 7
          let wednesday = 13
          let thursday = 19
          let friday = 25

          for(let i = 1; i <= 6; i++){
            let add: ScheduleSlot ={
              time: data.hours[i],
              monday: data.classes[0].classSchedule[i],
              tuesday: data.classes[0].classSchedule[tuesday],
              wednesday: data.classes[0].classSchedule[wednesday],
              thursday: data.classes[0].classSchedule[thursday],
              friday: data.classes[0].classSchedule[friday]
            }
            this.studentSchedule.push(add)
            monday++
            tuesday++
            wednesday++
            thursday++
            friday++
          }
        }
      })
    }

    if(response.status == 404){
      this.messageService.add({ severity: 'info', summary: 'Horário indisponível.', detail: 'O horário para sua turma ainda não esta disponível' })
    }
  }
  studentSchedule: ScheduleSlot[] = [];
  weekDays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];

  getClassContent(day: string, timeSlot: string): string {
    const slot = this.studentSchedule.find(slot => slot.time === timeSlot);
    return slot ? slot[day] || '' : '';
  }


}
