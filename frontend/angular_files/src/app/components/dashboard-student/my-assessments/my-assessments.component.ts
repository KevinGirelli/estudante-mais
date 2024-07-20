import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';

interface Assessment {
  id: string;
  assessmentName: string;
  teacherName: string;
  subject: string;
  date: string;
}

@Component({
  selector: 'app-my-assessments',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
  ],
  templateUrl: './my-assessments.component.html',
  styleUrl: './my-assessments.component.scss'
})
export class MyAssessmentsComponent {
  @ViewChild('dt2') dt2!: Table;

  searchValue = '';

  assessments: Assessment[] = [];

  filterAssessment(event: any) {
    if (this.dt2) {
      this.dt2.filterGlobal(event.target.value ?? '', 'contains');
    }
  }
}