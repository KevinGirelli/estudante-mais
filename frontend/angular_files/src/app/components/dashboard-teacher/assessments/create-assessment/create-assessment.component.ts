import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { CalendarModule } from 'primeng/calendar';

interface Class {
  id: string;
  name: string;
  subject: string;
}

@Component({
  selector: 'app-create-assessment',
  standalone: true,
  imports: [
    FormsModule,
    MultiSelectModule,
    CalendarModule
  ],
  templateUrl: './create-assessment.component.html',
  styleUrl: './create-assessment.component.scss'
})
export class CreateAssessmentComponent {
  assessmentName!: string;
  class = [];
  selectedClasses: Class[] = [];
  assessmentDate: Date | undefined;

  registerAssessment() {
    
  }
  
}
