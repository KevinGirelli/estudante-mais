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
  imports: [DropdownModule, FormsModule, ListboxModule, NgForOf, NgIf],
  templateUrl: './my-grades.component.html',
  styleUrls: ['./my-grades.component.scss']
})
export class MyGradesComponent implements OnInit {
  subjects: { label: string, value: string }[] = [];
  selectedSubject!: string;

  periods: { label: string, value: number }[] = [];
  selectedPeriod!: string;

  grades: Grade[] = [];

  allGrades: { [key: string]: { [key: string]: Grade[] } } = {};

  async ngOnInit(): Promise<void> {

    const quarterType = await fetch("http://localhost:8080/grades/getCurrentType",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(quarterType.status == 200){
      quarterType.json().then(data =>{
        for(let i = 0; i <= data; i++){
          if(data > 1){
            if(i == 2){
              const period = {
                label: "2º",
                value: 2
              }
              this.periods.push(period)
            }else if(i == 3){
              const period = {
                label: "3º",
                value: 3
              }
              this.periods.push(period)
            }
          }else{
            if(i == 0){
              const period = {
                label: "1º",
                value: 1
              }
              this.periods.push(period)
            } 
          }          
        }
      })
    }

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
            value:data[i].split(",")[0]
          }

          this.subjects.push(subject)
        }
      })
    }
  }

  async onSelectChange() {
    const response = await fetch("http://localhost:8080/grades/viewGrades/" + localStorage.getItem("userID") + "," + this.selectedSubject + "," + this.selectedPeriod,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      response.json().then(data => {
        console.log(data)
          for(let i = 0; i <= data.length-1; i++){
            const add: Grade = {
              label: `${data[i].date}: ${data[i].gradeValue}`,
            }
            this.grades.push(add)
          }

      })
    }
  }
}
