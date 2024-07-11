import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    StepperModule,
    ButtonModule,
    NgClass,
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  
  isMenuOpen = false;

  // Cadastro Alunos
  studentFullname!: string;
  studentEmail!: string;
  studentPassword!: string;
  studentCPF!: string;
  studentAge!: Date;
  classes!: string;

  cadastrarAluno() {
    const dados = {
      studentFullname: this.studentFullname,
      studentEmail: this.studentEmail,
      studentPassword: this.studentPassword,
      studentCPF: this.studentCPF,
      studentAge: this.studentAge,
      classes: this.classes
    };
  }

  // Cadastro de Professores 

  teacherName!: string;
  teacherEmail!: string;
  teacherPassword!: string;
  teacherSubject!: string;
  teacherCPF!: string;
  turmas!: string[];

  cadastrarProfessor() {
    const dados = {
      teacherEmail: this.teacherEmail,
      teacherPassword: this.teacherPassword,
      teacherName: this.teacherName,
      teacherSubject: this.teacherSubject,
      teacherCPF: this.teacherCPF,
      turmas: this.turmas
    };
  }

  // Cadastro de Turmas
  className!: string;
  gradeType!: string;
  gradeNumber!: number;
  classMonitor!: string;

  cadastrarTurma() {
    const dados = {
      className: this.className,
      gradeType: this.gradeType,
      gradeNumber: this.gradeNumber,
      classMonitor: this.classMonitor
    };
    console.log('Dados da turma:', dados);
  }
}