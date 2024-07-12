import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';
import { RegisterService } from '../../../services/register/register.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    StepperModule,
    ButtonModule,
    NgClass,
    FormsModule,
    HttpClientModule
  ],
  providers:[RegisterService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  isMenuOpen = false;

  // Cadastro Alunos
  Fullname!: string;
  email!: string;
  password!: string;
  cpf!: string;
  age!: Date;
  classID!: String;

  // Cadastro de Professores 
  teacherName!: string;
  teacherEmail!: string;
  teacherPassword!: string;
  teacherSubject!: string;
  teacherCPF!: string;
  turmas!: []
  
  // Cadastro de Turmas
  className!: string;
  gradeType!: string;
  gradeNumber!: number;
  classMonitor!: string;

  constructor(private registerService: RegisterService) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  cadastrarAluno() {
    const studentData = {
      Fullname: this.Fullname,
      email: this.email,
      password: this.password,
      cpf: this.cpf,
      age: this.age,
      classID: this.classID
    };

    this.registerService.registerStudent(studentData).subscribe(response => {
      console.log('Aluno registrado:', response);
    }, error => {
      console.error('Erro ao registrar aluno:', error);
    });
  }

  cadastrarProfessor() {
    const teacherData = {
      teacherEmail: this.teacherEmail,
      teacherPassword: this.teacherPassword,
      teacherName: this.teacherName,
      teacherSubject: this.teacherSubject,
      teacherCPF: this.teacherCPF,
      turmas: this.turmas
    };
    
    this.registerService.registerTeacher(teacherData).subscribe(response => {
      console.log('Professor registrado:', response);
    }, error => {
      console.error('Erro ao registrar professor:', error);
    });
  }

  cadastrarTurma() {
    const classData = {
      className: this.className,
      gradeType: this.gradeType,
      gradeNumber: this.gradeNumber,
      classMonitor: this.classMonitor
    };

    this.registerService.registerClass(classData).subscribe(response => {
      console.log('Turma registrada:', response);
    }, error => {
      console.error('Erro ao registrar turma:', error);
    });
  }
}