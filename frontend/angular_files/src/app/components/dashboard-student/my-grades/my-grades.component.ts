import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { NgForOf, NgIf } from '@angular/common';

interface Grade {
  label: string;
  value: number;
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

  periods: { label: string, value: string }[] = [];
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
            value:data[i].split(",")[0]
          }

          this.subjects.push(subject)
        }
      })
    }
  }

  async onSelectChange() {
    const response = await fetch("http://localhost:8080/grades/viewGrades/" + localStorage.getItem("userID") + "," + this.selectedSubject,{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 200){
      response.json().then(data => {
          //TO DO
      })
    }
  }
}
