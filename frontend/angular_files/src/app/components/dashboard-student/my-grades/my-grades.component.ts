import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { NgForOf, NgIf } from '@angular/common';

interface Grade {
  label: string;
}

@Component({
  selector: 'app-my-grades',
  standalone: true,
  imports: [
    DropdownModule,
    FormsModule, 
    ListboxModule, 
    NgForOf, 
    NgIf
  ],
  templateUrl: './my-grades.component.html',
  styleUrls: ['./my-grades.component.scss']
})
export class MyGradesComponent implements OnInit {
  subjects: { label: string, value: string, type: number }[] = [];
  selectedSubject!: string;

  periods: { label: string, value: number }[] = [];
  selectedPeriod!: string;

  grades: Grade[] = [];

  allGrades: { [key: string]: { [key: string]: Grade[] } } = {};

  async ngOnInit(): Promise<void> {

    const response1 = await fetch("http://localhost:8080/student/getSubjectsFromClasses/" + localStorage.getItem("userID"),{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response1.status == 200){
      response1.json().then(data =>{
        for(let i = 0; i <= data.length-1; i++){
          let subject = {
            label: data[i].split(",")[1],
            value: data[i].split(",")[0],
            type: data[i].split(",")[2]
          }

          this.subjects.push(subject)
        }
      })
    }
  }

  async onSelectChange() {
    this.grades = []
    const response = await fetch("http://localhost:8080/grades/viewGrades/" + localStorage.getItem("userID") + "," + this.selectedSubject + "," + this.selectedPeriod,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      response.json().then(data => {
          for(let i = 0; i <= data.length-1; i++){
            const add: Grade = {
              label: `${data[i].date}: ${data[i].gradeValue}`,
            }
            this.grades.push(add)
          }

      })
    }
  }

  async onSubjectSelected(){
   this.periods = []
   this.subjects.forEach(s =>{
    if(s.value == this.selectedSubject){
      if(s.type == 2){
        this.periods.push({label: "1°", value: 1})
        this.periods.push({label: "2°", value: 2})
        this.periods.push({label: "3°", value: 3})
        this.periods.push({label: "4°", value: 4})
        this.periods.push({label: "5°", value: 5})
      }

      if(s.type == 3){
        this.periods.push({label: "1°", value: 1})
        this.periods.push({label: "2°", value: 2})
        this.periods.push({label: "3°", value: 3})
        this.periods.push({label: "4°", value: 4})
      }

      if(s.type == 6){
        this.periods.push({label: "1°", value: 1})
        this.periods.push({label: "2°", value: 2})
      }
    }
   })
  }
}
