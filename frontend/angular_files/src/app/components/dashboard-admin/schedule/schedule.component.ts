import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

interface ClassSchedule {
  time: string;
  subject: string;
}

interface day {
  id: number,
  label: string
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
    NgIf,
    FormsModule,
    ToastModule
  ],
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss'],
  providers: [MessageService]
})

export class ScheduleComponent implements OnInit {  
  isMenuOpen = false;
  timeSlots: { time: string }[] = [];
  classes: string[] = [];
  schedule: { [key: string]: { [key: string]: string } } = {};

  scheduleData: any;

  days: day[] = [
    {id: 1, label: "Segunda-Feira"},
    {id: 2, label: "Terça-Feira"},
    {id: 3, label: "Quarta-Feira"},
    {id: 4, label: "Quinta-Feira"},
    {id: 5, label: "Sexta-Feira"}
  ]
  daySelected: number = 1;

  constructor(private router: Router, private messageService: MessageService) {
    this.timeSlots = Array.from(new Set(Object.values(this.schedule).flatMap(day => Object.keys(day))))
                          .map(time => ({ time }));
    this.classes = Object.keys(this.schedule);
  }

  updateTimeSlotsAndClasses() {
    this.timeSlots = Array.from(new Set(Object.values(this.schedule).flatMap(day => Object.keys(day))))
                          .map(time => ({ time }));
    this.classes = Object.keys(this.schedule);
  }

  convertJsonToTable(data: any){
    console.log(data)
    if(this.daySelected == 1){
      for(let i = 0; i <= data.classes.length-1; i++){
        if(data.classes[i].classSchedule[0]){
           this.schedule[data.classes[i].classSchedule[0]] = {}
        }   
      }
      if(data.hours.length == 60){
        for(let i = 1; i <= 12; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 30){
        for(let i = 1; i <= 5; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 90){
        for(let i = 1; i <= 17; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }
    }
    
    if(this.daySelected == 2){
      for(let i = 0; i <= data.classes.length-1; i++){
        if(data.classes[i].classSchedule[0]){
           this.schedule[data.classes[i].classSchedule[0]] = {}
        }   
      }
      if(data.hours.length == 60){
        for(let i = 13; i <= 23; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 30){
        for(let i = 7; i <= 11; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 90){
        for(let i = 19; i <= 35; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }
    }

    if(this.daySelected == 3){
      for(let i = 0; i <= data.classes.length-1; i++){
        if(data.classes[i].classSchedule[0]){
           this.schedule[data.classes[i].classSchedule[0]] = {}
        }   
      }
      if(data.hours.length == 60){
        for(let i = 25; i <= 35; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 30){
        for(let i = 13; i <= 17; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 90){
        for(let i = 37; i <= 53; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }
    }

    if(this.daySelected == 4){
      for(let i = 0; i <= data.classes.length-1; i++){
        if(data.classes[i].classSchedule[0]){
           this.schedule[data.classes[i].classSchedule[0]] = {}
        }   
      }
      if(data.hours.length == 60){
        for(let i = 37; i <= 47; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 30){
        for(let i = 19; i <= 23; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 90){
        for(let i = 55; i <= 71; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }
    }

    if(this.daySelected == 5){
      for(let i = 0; i <= data.classes.length-1; i++){
        if(data.classes[i].classSchedule[0]){
           this.schedule[data.classes[i].classSchedule[0]] = {}
        }   
      }
      if(data.hours.length == 60){
        for(let i = 49; i <= 59; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 30){
        for(let i = 25; i <= 29; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }

      if(data.hours.length == 90){
        for(let i = 73; i <= 89; i++){
          for(let c = 0; c <= data.classes.length-1; c++){
            if(data.classes[c].classSchedule[0]){
              this.schedule[data.classes[c].classSchedule[0]][data.hours[i]] = data.classes[c].classSchedule[i]
            }   
          }
        }
      }
    }


    this.updateTimeSlotsAndClasses()
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
        this.convertJsonToTable(data)
        this.scheduleData = data
      }) 
    }

    if(response.status == 400){
      this.messageService.add({ severity: 'info', summary: 'Nenhum horário encontrado.', detail: 'É necessário gerar o horário.' })
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  fetchScheduleChange() {
    this.convertJsonToTable(this.scheduleData)
  }

  getClassContent(className: string, timeSlot: string): string {
    return this.schedule[className]?.[timeSlot] || '';
  }

  async generateNewSchedule() {
    const response = await fetch("http://localhost:8080/admin/schedule/genSchedule",{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    if(response.status == 302){
      const response2 = await fetch("http://localhost:8080/admin/schedule/getSchedule",{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if(response2.status == 200){
        response.json().then(data =>{
          this.convertJsonToTable(data)
          this.scheduleData = data
        }) 
      }
      this.messageService.add({ severity: 'info', summary: 'Horário atualizado', detail: 'Horário de aulas atualizado.' })
    }

    if(response.status == 409){
      response.json().then(data =>{
        this.messageService.add({ severity: 'info', summary: 'Geração cancelada', detail: `Geração cancelada devido 
          ao limite de aulas excedido pelo professor(a): ${data.message}, por favor remova ${data.secondMessage} aulas deste professor(a) para prosseguir.` })
      })
    }

    if(response.status == 200){
      const response2 = await fetch("http://localhost:8080/admin/schedule/getSchedule",{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if(response2.status == 200){
        response2.json().then(data =>{
          this.convertJsonToTable(data)
          this.scheduleData = data
        }) 
        this.messageService.add({ severity: 'success', summary: 'Horario gerado com sucesso', detail: 'Horário de aulas gerado.' })
      }  
    }

    if(response.status == 400){
      this.messageService.add({ severity: 'info', summary: 'Geração não executada', detail: 'Por favor, atualize as configurações de geração em (Ano Letivo)' })
    }
  }

  async deleteSchedule() {
    const response = await fetch("http://localhost:8080/admin/schedule/deleteSchedule",{
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    if(response.status == 200){
      this.schedule = {}
      this.timeSlots = []
      this.scheduleData = []
      this.messageService.add({ severity: 'success', summary: 'Horario deletado com sucesso', detail: 'Horário de aulas deletado.' })
    }
  }

  logout() {
    
  }
}