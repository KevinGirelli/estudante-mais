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

  allGrades: { [key: string]: { [key: string]: Grade[] } } = {
    math: {
      first: [
        { label: 'Prova 1: 8.5', value: 8.5 },
        { label: 'Prova 2: 7.0', value: 7.0 }
      ],
      second: [
        { label: 'Prova 1: 9.0', value: 9.0 },
        { label: 'Prova 2: 8.0', value: 8.0 }
      ],
      third: [
        { label: 'Prova 1: 7.5', value: 7.5 },
        { label: 'Prova 2: 8.5', value: 8.5 }
      ]
    },
    portuguese: {
      first: [
        { label: 'Prova 1: 7.5', value: 7.5 },
        { label: 'Prova 2: 8.0', value: 8.0 }
      ],
      second: [
        { label: 'Prova 1: 8.5', value: 8.5 },
        { label: 'Prova 2: 7.5', value: 7.5 }
      ],
      third: [
        { label: 'Prova 1: 9.0', value: 9.0 },
        { label: 'Prova 2: 8.5', value: 8.5 }
      ]
    },
    history: {
      first: [
        { label: 'Prova 1: 6.5', value: 6.5 },
        { label: 'Prova 2: 7.0', value: 7.0 }
      ],
      second: [
        { label: 'Prova 1: 7.5', value: 7.5 },
        { label: 'Prova 2: 8.0', value: 8.0 }
      ],
      third: [
        { label: 'Prova 1: 8.0', value: 8.0 },
        { label: 'Prova 2: 9.0', value: 9.0 }
      ]
    }
  };

  ngOnInit() {
    this.subjects = [
      { label: 'Matemática', value: 'math' },
      { label: 'Português', value: 'portuguese' },
      { label: 'História', value: 'history' }
    ];

    this.periods = [
      { label: '1º Trimestre', value: 'first' },
      { label: '2º Trimestre', value: 'second' },
      { label: '3º Trimestre', value: 'third' }
    ];
  }

  onSelectChange() {
    if (this.selectedSubject && this.selectedPeriod) {
      this.grades = this.allGrades[this.selectedSubject][this.selectedPeriod];
    } else {
      this.grades = [];
    }
  }
}
