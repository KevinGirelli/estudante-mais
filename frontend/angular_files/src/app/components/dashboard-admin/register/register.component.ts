import { NgClass, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';
import { RegisterService } from '../../../services/register/register.service';
import { HttpClientModule } from '@angular/common/http';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';

interface Subject {
  name: string
}

interface classes {
  id: string;
  name: string;
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
  ],
  providers: [RegisterService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit{
  isMenuOpen = false;
  classesSelected: string = "";

  allClasses: classes[] = [];

  ngOnInit(): void {
    const allClasses = localStorage.getItem("classes");
    
    if(allClasses){
      const parsedData= JSON.parse(allClasses)
    
      const keys = Object.keys(parsedData);
      let index = 0
      keys.forEach(key => {
        const value = parsedData[key];
        const addClass: classes = {id: value.classID, name: value.className}
        this.allClasses.push(addClass);
      });
    };
  }

  // Cadastro Alunos
  Fullname!: string;
  email!: string;
  password!: string;
  cpf!: string;
  age!: Date;
  classID!: string;

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
  gradeNumber!: Array<number>;
  classMonitor!: string;

  gradeNumbers: number[] = [];

  constructor(private registerService: RegisterService) {
    this.subjects = [
      { name: 'Arte'},
      { name: 'Biologia'},
      { name: 'Educação Física'},
      { name: 'Ensino Religioso'},
      { name: 'Filosofia'},
      { name: 'Física'},
      { name: 'Geografia'},
      { name: 'História'},
      { name: 'Língua Inglesa'},
      { name: 'Língua Portuguesa'},
      { name: 'Matemática'},
      { name: 'Química'},
      { name: 'Sociologia'}
    ];
    
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  updateGradeNumbers() {

    if (this.gradeType === 'Fundamental 1') {
      this.gradeNumbers = [1, 2, 3, 4, 5];
    } else if (this.gradeType === 'Fundamental 2') {
      this.gradeNumbers = [6, 7, 8, 9];
    } else if (this.gradeType === 'Ensino Médio') {
      this.gradeNumbers = [1, 2, 3];
    } else {
      this.gradeNumbers = [];
      this.gradeNumber = [];
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
      classID: this.classesSelected
    };

    this.registerService.registerStudent(studentData).subscribe(response => {
      console.log('Aluno registrado:', response);
    }, error => {
      console.error('Erro ao registrar aluno:', error);
    });
  }

  cadastrarProfessor() {
    const subjectsToSend = this.selectedSubjects.map(subject => subject.name);
    const teacherData = {
      teacherEmail: this.teacherEmail,
      teacherPassword: this.teacherPassword,
      teacherName: this.teacherName,
      teacherCPF: this.teacherCPF,
      subjects: subjectsToSend
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
      gradeNumber: this.gradeNumber
    };

    this.registerService.registerClass(classData).subscribe(response => {
      console.log('Turma registrada:', response);
    }, error => {
      console.error('Erro ao registrar turma:', error);
    });
  }
}
