import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CalendarModule } from 'primeng/calendar';
import { StepperModule } from 'primeng/stepper';
import { ListboxModule } from 'primeng/listbox';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

interface Student {
  id: number;
  name: string;
  grade: string | null;
}

@Component({
  selector: 'app-edit-assessment',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    CalendarModule,
    StepperModule,
    ListboxModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './edit-assessment.component.html',
  styleUrls: ['./edit-assessment.component.scss']
})
export class EditAssessmentComponent implements OnInit {
  assessmentId: string | null = null;
  assessmentName: string = '';
  assessmentDate: Date | null = null;
  classesSelected: string = '';
  subjectSelected: string = '';
  allClasses: { id: string, name: string }[] = [];
  allSubjects: { id: string, subjectName: string }[] = [];
  selectedStudents: Student[] = [];

  students = [
    { id: 1, name: 'João da Silva', grade: null },
    { id: 2, name: 'Maria Oliveira', grade: null },
    { id: 3, name: 'Carlos Santos', grade: null },
    { id: 4, name: 'Ana Costa', grade: null }
  ];

  constructor(private route: ActivatedRoute, private router: Router, private messageService: MessageService) {}

  async ngOnInit(): Promise<void> {
    this.assessmentId = this.route.snapshot.paramMap.get('id');
    
    // Fetch all classes and subjects for the dropdowns
    await this.fetchClassesAndSubjects();

    if (this.assessmentId) {
      // Fetch the assessment data from your backend using this.assessmentId
      const response = await fetch(`http://localhost:8080/assess/getAssessment/${this.assessmentId}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.status === 200) {
        const data = await response.json();
        this.assessmentName = data.name;
        this.assessmentDate = new Date(data.date);
        this.classesSelected = data.classID;
        this.subjectSelected = data.subjectID;

        // Fetch students for the selected class and subject
        await this.fetchStudents();
      } else if (response.status === 403) {
        this.router.navigate(['403']);
      }
    }
  }

  async fetchClassesAndSubjects(): Promise<void> {
    // Fetch all classes
    const classResponse = await fetch('http://localhost:8080/classes', {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    if (classResponse.status === 200) {
      this.allClasses = await classResponse.json();
    }

    // Fetch all subjects
    const subjectResponse = await fetch('http://localhost:8080/subjects', {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    if (subjectResponse.status === 200) {
      this.allSubjects = await subjectResponse.json();
    }
  }

  async fetchStudents(): Promise<void> {
    // Fetch students for the selected class and subject
    const response = await fetch(`http://localhost:8080/assess/getStudentsForAssessment/${this.assessmentId}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });

    if (response.status === 200) {
      const data = await response.json();
      this.students = data.map((student: any) => ({
        id: student.id,
        name: student.name,
        grade: student.grade || null
      }));
    }
  }

  async postGrades(): Promise<void> {
    const grades = this.students.map(student => ({
      id: student.id,
      grade: student.grade
    }));

    const response = await fetch(`http://localhost:8080/assess/postGrades`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({ assessmentId: this.assessmentId, grades })
    });

    if (response.status === 200) {
      this.messageService.add({ severity: 'success', summary: 'Notas postadas', detail: 'Notas postadas com sucesso!' });
      this.router.navigate(['/teacher/assessments']);
    } else if (response.status === 403) {
      this.router.navigate(['403']);
    } else {
      this.messageService.add({ severity: 'error', summary: 'Erro ao postar notas', detail: 'Erro ao postar notas!' });
    }
  }

  async updateAssessment(): Promise<void> {
    const updatedAssessment = {
      id: this.assessmentId,
      name: this.assessmentName,
      date: this.assessmentDate,
      classID: this.classesSelected,
      subjectID: this.subjectSelected
    };

    const response = await fetch(`http://localhost:8080/assess/updateAssessment/${this.assessmentId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify(updatedAssessment)
    });

    if (response.status === 200) {
      this.messageService.add({ severity: 'success', summary: 'Avaliação atualizada', detail: 'Avaliação atualizada com sucesso!' });
      this.router.navigate(['/teacher/assessments']);
    } else if (response.status === 403) {
      this.router.navigate(['403']);
    } else {
      this.messageService.add({ severity: 'error', summary: 'Erro ao atualizar avaliação', detail: 'Erro ao atualizar avaliação!' });
    }
  }
}
