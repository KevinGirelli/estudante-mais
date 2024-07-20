import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';

interface Assessment {
  id: string;
  assessmentName: string;
  class: string;
  subject: string;
  date: string;
}

@Component({
  selector: 'app-list-assessment',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FormsModule,
  ],
  templateUrl: './list-assessment.component.html',
  styleUrls: ['./list-assessment.component.scss']
})
export class ListAssessmentComponent {
  @ViewChild('dt2') dt2!: Table;

  searchValue = '';

  assessments: Assessment[] = [];

  filterAssessment(event: any) {
    if (this.dt2) {
      this.dt2.filterGlobal(event.target.value ?? '', 'contains');
    }
  }
}
