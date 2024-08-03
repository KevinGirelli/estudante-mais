import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';

interface DiaryEntry {
  date: Date;
  subject: string;
  content: string;
  attendance: string;
  class: string;
}

@Component({
  selector: 'app-class-diary',
  standalone: true,
  imports: [
    CommonModule,
    TabViewModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    CalendarModule,
    DropdownModule,
    FormsModule
  ],
  templateUrl: './class-diary.component.html',
  styleUrls: ['./class-diary.component.scss']
})
export class ClassDiaryComponent {
  classOptions = [
    { label: '351', value: '351' },
    { label: '352', value: '352' },
  ];

  subjectOptions = [
    { label: 'Matemática', value: 'Matemática' },
    { label: 'Português', value: 'Português' },
    { label: 'História', value: 'História' },
    { label: 'Geografia', value: 'Geografia' },
  ];

  selectedClass: string | null = null;
  classDiary: DiaryEntry[] = [
    { date: new Date('08/03/2024'), subject: 'Matemática', content: 'Equações do 2º grau', attendance: 'Todos presentes', class: '351' },
  ];

  displayAddDiaryModal = false;
  newDiary: DiaryEntry = { date: new Date(), subject: '', content: '', attendance: '', class: '' };

  get filteredDiaryEntries(): DiaryEntry[] {
    return this.classDiary.filter(entry => entry.class === this.selectedClass);
  }

  showAddDiaryModal() {
    if (this.selectedClass) {
      this.newDiary = { date: new Date(), subject: '', content: '', attendance: '', class: this.selectedClass };
      this.displayAddDiaryModal = true;
    }
  }

  addDiary() {
    if (this.newDiary.date && this.newDiary.subject && this.newDiary.content && this.newDiary.attendance) {
      this.classDiary.push(this.newDiary);
      this.displayAddDiaryModal = false;
    }
  }

  editDiary(diary: DiaryEntry) {
  }

  deleteDiary(diary: DiaryEntry) {
    this.classDiary = this.classDiary.filter(d => d !== diary);
  }
}
