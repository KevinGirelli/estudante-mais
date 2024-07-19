import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';

interface Subject {
  id: string
  name: string;
}

interface Teacher {
  teacherName: string;
  teacherEmail: string;
  teacherCPF: string;
  subjects: Subject[];
}

@Component({
  selector: 'app-teachers-from-class',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './teachers-from-class.component.html',
  styleUrls: ['./teachers-from-class.component.scss']
})
export class TeachersFromClassComponent {
  
  isMenuOpen = false;
  teachers: Teacher[] = [];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  getSubjectsString(subjects: Subject[]): string {
    return subjects.map(subject => subject.name).join(', ');
  }
}
