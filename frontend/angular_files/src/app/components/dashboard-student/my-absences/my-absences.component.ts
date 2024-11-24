import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { NgForOf, NgIf } from '@angular/common';

interface Absence {
  label: string;
  date: string;
  count: number;
}

@Component({
  selector: 'app-my-absences',
  standalone: true,
  imports: [
    DropdownModule, 
    FormsModule, 
    ListboxModule, 
    NgForOf, 
    NgIf
  ],
  templateUrl: './my-absences.component.html',
  styleUrls: ['./my-absences.component.scss']
})
export class MyAbsencesComponent implements OnInit{
  subjects: { label: string, value: string, type: number }[] = [];
  selectedSubject!: string;

  periods: { label: string, value: number }[] = [];
  selectedPeriod!: string;

  absences: Absence[] = [];
  totalAbsences: number = 0;

  async ngOnInit(): Promise<void> {
    const quarterType = await fetch("http://localhost:8080/grades/getCurrentType",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

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
            value:data[i].split(",")[0],
            type: data[i].split(",")[2]
          }
          this.subjects.push(subject)
        }
      })
    }
  }

  async onSelectChange() {
    const response = await fetch("http://localhost:8080/absense/getAbsenseFromSubject/" + this.selectedSubject + 
      "," + this.selectedPeriod + "," + localStorage.getItem("userID")
    ,{
      method: "GET",
      headers:{
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      response.json().then(data => {
        this.absences = []
        this.totalAbsences = 0
        let total = 0
        for(let i = 0; i <= data.length-1; i++){
          total += data[i].quantity;
          let addAbsence: Absence = {
            label: "",
            date: data[i].date,
            count: data[i].quantity
          }
          this.absences.push(addAbsence)
          this.totalAbsences = total
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
       }
 
       if(s.type == 6){
         this.periods.push({label: "1°", value: 1})
         this.periods.push({label: "2°", value: 2})
       }
     }
    })
   }
}
