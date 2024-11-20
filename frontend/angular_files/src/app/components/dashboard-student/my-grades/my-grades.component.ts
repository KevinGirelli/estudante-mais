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
    this.periods = [
      { label: '1°', value: 1 },
      { label: '2°', value: 2 },
      { label: '3°', value: 3 }
    ];

    this.selectedPeriod = 1;
    await this.onPeriodSelected();
  }

  async onPeriodSelected(): Promise<void> {
    this.subjects = [
      { label: 'Matemática', value: 'math', type: 1 },
      { label: 'Português', value: 'port', type: 2 },
      { label: 'História', value: 'hist', type: 3 },
    ];

    this.selectedSubject = 'math';
    await this.onSelectChange();
  }

  async onSelectChange(): Promise<void> {
    this.grades = [
      { label: '8.5' },
      { label: '7.0' },
      { label: '9.0' },
    ];
  }

  async generateBoletim(): Promise<void> {
    if (!this.selectedPeriod) return;
  
    const mockData: { [key: string]: number[] } = {
      math: [8.0, 7.5, 9.0, 8.5, 7.8],
      port: [7.0, 8.0, 8.5, 7.5, 9.2],
      hist: [9.0, 8.5, 7.0, 9.0, 8.8],
    };
  
    const boletim = this.subjects.map((subject) => {
      const notas = mockData[subject.value] || [];
      const medias: { [key: string]: string } = {};
  
      for (let i = 1; i <= this.selectedPeriod; i++) {
        const media = this.calculateAverage(notas.slice(0, i));
        medias[`Média ${i}`] = media.toFixed(2);
      }
  
      const mediaFinal = this.calculateAverage(notas.slice(0, this.selectedPeriod));
  
      return {
        Matéria: subject.label,
        ...medias,
        'Média Final': mediaFinal.toFixed(2),
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
