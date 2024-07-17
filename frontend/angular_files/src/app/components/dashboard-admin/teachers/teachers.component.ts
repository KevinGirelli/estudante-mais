import { Component, ViewChild } from '@angular/core';
import { NgClass, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';

interface Teacher {
  teacherName: string;
  teacherEmail: string;
  teacherCPF: string;
  subjects: string[];
}

@Component({
  selector: 'app-teachers',
  standalone: true,
  imports: [
    NgClass,
    CommonModule,
    TableModule,
    ButtonModule,
    FormsModule,
 ],
  providers: [FormsModule],
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent {
  @ViewChild('dt2') dt2!: Table;

  isMenuOpen = false;
  teachers: Teacher[] = [
  ];
  searchValue = '';

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  filterTeachers(event: any) {
    if (this.dt2) {
      this.dt2.filterGlobal(event.target.value ?? '', 'contains');
    }
  }
  
}