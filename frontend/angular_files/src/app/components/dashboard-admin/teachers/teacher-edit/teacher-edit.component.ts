import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';
import { NgClass } from '@angular/common';

interface Subject {
  name: string;
}

@Component({
  selector: 'app-teacher-edit',
  standalone: true,
  imports: [
    FormsModule,
    InputMaskModule,
    MultiSelectModule,
    NgClass
  ],
  templateUrl: './teacher-edit.component.html',
  styleUrls: ['./teacher-edit.component.scss']
})
export class TeacherEditComponent implements OnInit {
  isMenuOpen = false;

  teacherName: string = '';
  teacherEmail: string = '';
  teacherPassword: string = '';
  teacherCPF: string = '';
  selectedSubjects: Subject[] = [];
  subjects: Subject[] = [];

  constructor(private dataSaverService: DataSaverService) {}

  ngOnInit(): void {
    const teacher = this.dataSaverService.getData();

    if (teacher) {
      this.teacherName = teacher.teacherName;
      this.teacherEmail = teacher.teacherEmail;
      this.teacherPassword = teacher.teacherPassword;
      this.teacherCPF = teacher.teacherCPF;
      this.selectedSubjects = teacher.subjects ? teacher.subjects.map((subject: string) => ({ name: subject })) : [];
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  editarProfessor() {
  }
}
