import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface ScheduleSlot {
  time: string;
  [key: string]: string | undefined;
}

interface Class {
  id: any,
  name: any
}

@Component({
  selector: 'app-teacher-schedule',
  standalone: true,
  imports: [
    NgClass,
    TableModule,
    NgFor,
    HttpClientModule,
    NgIf,
    FormsModule
  ],
  templateUrl: './teacher-schedule.component.html',
  styleUrls: ['./teacher-schedule.component.scss']
})


export class TeacherScheduleComponent implements OnInit {
  async ngOnInit(): Promise<void> {
    const response = await fetch("http://localhost:8080/teacher/getAllClassesFromTeacher/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }    
    })

    if(response.status == 200){
      response.json().then(data =>{
        const keys = Object.keys(data);
        for(let i = 0; i <= keys.length-1; i++){
          const addClass: Class = {
            id: data[i].classID,
            name: data[i].className
          }
          let alreadExist = false
          this.allClasses.forEach(c =>{
            if(c.id == addClass.id){
              alreadExist = true
            }
          })
          if(alreadExist == false){
            this.allClasses.push(addClass)
          }
        }
      })
    }
  }

  async onClassesChange(){
    const response = await fetch("http://localhost:8080/teacher/getTeacherSchedule/" + this.classesSelected,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }    
    })

    if(response.status == 200){
      response.json().then(data =>{
        this.teacherSchedule = []
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

            if(data.classes[0].classSchedule[i].includes(localStorage.getItem("username"))){
              add["monday"] = localStorage.getItem("username")?.toString()
            }else{
              add["monday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[tuesday].includes(localStorage.getItem("username"))){
              add["tuesday"] = localStorage.getItem("username")?.toString()
            }else{
              add["tuesday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[wednesday].includes(localStorage.getItem("username"))){
              add["wednesday"] = localStorage.getItem("username")?.toString()
            }else{
              add["wednesday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[thursday].includes(localStorage.getItem("username"))){
              add["thursday"] = localStorage.getItem("username")?.toString()
            }else{
              add["thursday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[friday].includes(localStorage.getItem("username"))){
              add["friday"] = localStorage.getItem("username")?.toString()
            }else{
              add["friday"] = "---(---)"
            }

            this.teacherSchedule.push(add)
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

            if(data.classes[0].classSchedule[i].includes(localStorage.getItem("username"))){
              add["monday"] = localStorage.getItem("username")?.toString()
            }else{
              add["monday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[tuesday].includes(localStorage.getItem("username"))){
              add["tuesday"] = localStorage.getItem("username")?.toString()
            }else{
              add["tuesday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[wednesday].includes(localStorage.getItem("username"))){
              add["wednesday"] = localStorage.getItem("username")?.toString()
            }else{
              add["wednesday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[thursday].includes(localStorage.getItem("username"))){
              add["thursday"] = localStorage.getItem("username")?.toString()
            }else{
              add["thursday"] = "---(---)"
            }

            if(data.classes[0].classSchedule[friday].includes(localStorage.getItem("username"))){
              add["friday"] = localStorage.getItem("username")?.toString()
            }else{
              add["friday"] = "---(---)"
            }

            this.teacherSchedule.push(add)
            monday++
            tuesday++
            wednesday++
            thursday++
            friday++
          }
        }
      })
    }
  }

  allClasses: Class[] = [];
  classesSelected: any = "";

  teacherSchedule: ScheduleSlot[] = [];
  weekDays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];

  getClassContent(day: string, timeSlot: string): string {
    const slot = this.teacherSchedule.find(slot => slot.time === timeSlot);
    return slot ? slot[day] || '' : '';
  }

}

