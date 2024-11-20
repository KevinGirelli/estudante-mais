import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { NgIf } from '@angular/common';
import * as XLSX from 'xlsx';

interface Grade {
  label: string;
}

interface Period {
  label: string;
  value: number;
}

interface Subject {
  label: string;
  value: string;
  type: number;
}

@Component({
  selector: 'app-my-grades',
  standalone: true,
  imports: [
    DropdownModule,
    FormsModule,
    ListboxModule,
    NgIf
  ],
  templateUrl: './my-grades.component.html',
  styleUrls: ['./my-grades.component.scss']
})
export class MyGradesComponent implements OnInit {
  periods: Period[] = [];
  selectedPeriod!: number;

  subjects: Subject[] = [];
  selectedSubject!: string;

  grades: Grade[] = [];

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

  async onPeriodSelected(): Promise<void> {
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

  async onSelectChange(): Promise<void> {
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

  async generateBoletim() {
    if (!this.selectedPeriod) return;
  
    const mockData: { [key: string]: number[] } = {};
    let allGrades: string[] = []

    await Promise.all(
      this.subjects.map(async (s) => {
        for(let i = 1; i <= this.selectedPeriod; i++){
          const response = await fetch(
            `http://localhost:8080/grades/viewGrades/${localStorage.getItem("userID")},${s.value},${i}`,
            {
              method: "GET",
              headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
              },
            }
          );
    
          if (response.status === 200) {
            const data = await response.json();
            data.map((item: any) => allGrades.push(`${item.gradeValue}@${i}@${s.label}`));
          }
        }
      })
    );
  
    const boletim = this.subjects.map((subject) => {
      
      const medias: { [key: string]: string } = {};
      let notas: number[] = []
  
      for (let i = 1; i <= this.selectedPeriod; i++) {
        let filterGrades: number[] = []
        
        allGrades.forEach(g =>{
          let subjectNotas = g.split("@")
          if(subjectNotas[2] == subject.label && parseInt(subjectNotas[1]) == i){
            filterGrades.push(parseFloat(subjectNotas[0]))
          }
        })
        mockData[subject.label] = filterGrades
        
        notas = mockData[subject.label]
        const media = this.calculateAverage(notas);
        medias[`Média ${i}`] = media.toFixed(2);
      }

      const total = Object.values(medias).map(Number).reduce((acc, curr) => acc + curr, 0);
      const mediaFinal = total
  
  
      return {
        Matéria: subject.label,
        ...medias,
        'Nota Total': mediaFinal.toFixed(2),
      };
    });
  
    const worksheet = XLSX.utils.json_to_sheet(boletim);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Boletim');
  
    XLSX.writeFile(workbook, `boletim-trimestre-${this.selectedPeriod}.xlsx`);
  }
  
  calculateAverage(notas: number[]): number {
    if (notas.length === 0) return 0;
    const sum = notas.reduce((acc, curr) => acc + curr, 0);
    return sum / notas.length;
  }
  
}  
