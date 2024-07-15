import { NgClass, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';
import { RegisterService } from '../../../services/register/register.service';
import { HttpClientModule } from '@angular/common/http';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';
import { TreeSelectModule } from 'primeng/treeselect';
import { TreeNode } from 'primeng/api';

interface Subject {
  name: string,
  code: string
}

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    StepperModule,
    ButtonModule,
    NgClass,
    FormsModule,
    HttpClientModule,
    InputMaskModule,
    NgFor,
    MultiSelectModule,
    TreeSelectModule
  ],
  providers: [RegisterService],
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
  classID!: string;
  
  classNodes: TreeNode[] = [];
  selectedClassNode: TreeNode | undefined;

  // Cadastro de Professores
  teacherName!: string;
  teacherEmail!: string;
  teacherPassword!: string;
  teacherCPF!: string;
  turmas!: string[];
  subjects!: Subject[];
  selectedSubjects: Subject[] = [];

  // Cadastro de Turmas
  className!: string;
  gradeType!: string;
  gradeNumber!: string;
  classMonitor!: string;

  gradeNumbers: string[] = [];

  classMonitorNodes: TreeNode[] = [];
  selectedClassMonitorNode: TreeNode | undefined;

  constructor(private registerService: RegisterService) {
    this.subjects = [
      { name: 'Arte', code: 'ART' },
      { name: 'Biologia', code: 'BIO' },
      { name: 'Educação Física', code: 'EDF' },
      { name: 'Ensino Religioso', code: 'REL' },
      { name: 'Filosofia', code: 'FIL' },
      { name: 'Física', code: 'FIS' },
      { name: 'Geografia', code: 'GEO' },
      { name: 'História', code: 'HIS' },
      { name: 'Língua Inglesa', code: 'LEI' },
      { name: 'Língua Portuguesa', code: 'LPT' },
      { name: 'Matemática', code: 'MAT' },
      { name: 'Química', code: 'QUI' },
      { name: 'Sociologia', code: 'SOC' }
    ];
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  updateGradeNumbers() {
    if (this.gradeType === 'Fundamental 1') {
      this.gradeNumbers = ['Primeiro', 'Segundo', 'Terceiro', 'Quarto', 'Quinto'];
    } else if (this.gradeType === 'Fundamental 2') {
      this.gradeNumbers = ['Sexto', 'Sétimo', 'Oitavo', 'Nono'];
    } else if (this.gradeType === 'Ensino Médio') {
      this.gradeNumbers = ['Primeirão', 'Segundão', 'Terceirão'];
    } else {
      this.gradeNumbers = [];
      this.gradeNumber = '';
    }
  }

  isFormValid(): boolean {
    return !!this.gradeType && !!this.gradeNumber;
  }

  cadastrarAluno() {
    const studentData = {
      Fullname: this.Fullname,
      email: this.email,
      password: this.password,
      cpf: this.cpf,
      age: this.age,
      classID: this.selectedClassNode?.key
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
      teacherCPF: this.teacherCPF,
      turmas: this.turmas,
      subjects: this.selectedSubjects
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
