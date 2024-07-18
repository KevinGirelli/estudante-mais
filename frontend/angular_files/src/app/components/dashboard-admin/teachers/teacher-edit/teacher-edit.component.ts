import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';
import { NgClass, NgIf } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { ListboxModule } from 'primeng/listbox';
import { ToastModule } from 'primeng/toast';

interface Subject {
  name: string;
}

interface TeacherClass {
  className: string;
  subjectName: string;
  quantity: number;
}

@Component({
  selector: 'app-teacher-edit',
  standalone: true,
  imports: [
    FormsModule,
    InputMaskModule,
    MultiSelectModule,
    NgClass,
    ToastModule,
    DialogModule,
    ListboxModule,
    NgIf
  ],
  templateUrl: './teacher-edit.component.html',
  styleUrls: ['./teacher-edit.component.scss']
})
export class TeacherEditComponent implements OnInit {
  isMenuOpen = false;
  visible: boolean = false;

  teacherClasses: TeacherClass[] = [
    { className: '351', subjectName: 'Geografia', quantity: 2 },
    { className: '351', subjectName: 'História', quantity: 3 },
    { className: '352', subjectName: 'História', quantity: 3 },
    { className: '353', subjectName: 'Geografia', quantity: 2 },
    { className: '354', subjectName: 'História', quantity: 3 },
  ];

  selectedClasses: TeacherClass[] = [];
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

  addClass(teacherClass: TeacherClass) {
    if (!this.selectedClasses.includes(teacherClass)) {
      this.selectedClasses.push(teacherClass);
    }
  }

  removeClass(teacherClass: TeacherClass) {
    const index = this.selectedClasses.indexOf(teacherClass);
    if (index > -1) {
      this.selectedClasses.splice(index, 1);
    }
  }

  confirmClasses() {
    console.log('Classes confirmadas:', this.selectedClasses);
    this.visible = false;
  }

  selecionarAulas() {
    this.visible = true;
  }

  editarProfessor() {
    
  }
}