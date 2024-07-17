import { NgClass, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';
import { RegisterService } from '../../../services/register/register.service';
import { HttpClientModule } from '@angular/common/http';
import { InputMaskModule } from 'primeng/inputmask';
import { MultiSelectModule } from 'primeng/multiselect';
import { ToastModule } from 'primeng/toast';
import { MessageService} from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { ListboxModule } from 'primeng/listbox';

interface Subject {
  name: string;
  quantity: number;
}
interface Class {
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
    ToastModule,
    DialogModule,
    ListboxModule
  ],
  providers: [RegisterService, MessageService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {
  isMenuOpen = false;
  classesSelected: string = "";
  visible: boolean = false;

  allClasses: Class[] = [];
  subjects: Subject[] = [];
  subjectsClasses: Subject[] = [];

  ngOnInit(): void {
    fetch("http://localhost:8080/auth/verifyAdminToken",{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
      }
    })

    fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        console.log("REDIRECT")
      }

      if(res.status == 200){
        res.json().then(data => {
          console.log(data)
          localStorage.setItem("classes",JSON.stringify(data))
          console.log("data loaded sucessfuly.")
        })
      }
    })

    const allClasses = localStorage.getItem("classes");
    
    if(allClasses){
      const parsedData= JSON.parse(allClasses)
    
      const keys = Object.keys(parsedData);
      keys.forEach(key => {
        const value = parsedData[key];
        const addClass: Class = {id: value.classID, name: value.className}
        this.allClasses.push(addClass);
      });
    };

    this.subjects = [
      { name: 'Arte', quantity: 0 },
      { name: 'Biologia', quantity: 0 },
      { name: 'Educação Física', quantity: 0 },
      { name: 'Ensino Religioso', quantity: 0 },
      { name: 'Filosofia', quantity: 0 },
      { name: 'Física', quantity: 0 },
      { name: 'Geografia', quantity: 0 },
      { name: 'História', quantity: 0 },
      { name: 'Língua Inglesa', quantity: 0 },
      { name: 'Língua Portuguesa', quantity: 0 },
      { name: 'Matemática', quantity: 0 },
      { name: 'Química', quantity: 0 },
      { name: 'Sociologia', quantity: 0 }
    ];
  }

  // Cadastro Alunos
  Fullname: string = '';
  email: string = '';
  password: string = '';
  cpf: string = '';
  age: Date = new Date('0000-00-00');
  classID: string = '';

  // Cadastro de Professores
  teacherName: string = '';
  teacherEmail: string = '';
  teacherPassword: string = '';
  teacherCPF: string = '';
  selectedSubjects: Subject[] = [];

  // Cadastro de Turmas
  className: string = '';
  gradeType: string = '';
  gradeNumber: number[] = [];
  classMonitor: string = '';

  gradeNumbers: number[] = [];

  constructor(private registerService: RegisterService, private messageService: MessageService) {}

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
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Aluno registrado com sucesso!' });
      this.clearAlunoForm();
    }, error => {
      console.error('Erro ao registrar aluno:', error);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar aluno!' });
    });
  }

  cadastrarProfessor() {
    const teacherData = {
      teacherEmail: this.teacherEmail,
      teacherPassword: this.teacherPassword,
      teacherName: this.teacherName,
      teacherCPF: this.teacherCPF,
      subjects: this.selectedSubjects
    };

    this.registerService.registerTeacher(teacherData).subscribe(response => {
      console.log('Professor registrado:', response);
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Professor registrado com sucesso!' });
      this.clearProfessorForm();
    }, error => {
      console.error('Erro ao registrar professor:', error);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar professor!' });
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
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Turma registrada com sucesso!' });
      this.clearTurmaForm();
    }, error => {
      console.error('Erro ao registrar turma:', error);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar turma!' });
    });
  }

  clearAlunoForm() {
    this.Fullname = '';
    this.email = '';
    this.password = '';
    this.cpf = '';
    this.age = new Date('0000-00-00');
    this.classesSelected = '';
  }

  clearProfessorForm() {
    this.teacherName = '';
    this.teacherEmail = '';
    this.teacherPassword = '';
    this.teacherCPF = '';
    this.selectedSubjects = [];
  }

  clearTurmaForm() {
    this.className = '';
    this.gradeType = '';
    this.gradeNumber = [];
  }

  selecionarMateria() {
    this.visible = true;
  }

  increaseQuantity(subject: Subject) {
    subject.quantity++;
  }

  decreaseQuantity(subject: Subject) {
    if (subject.quantity > 0) {
      subject.quantity--;
    }
  }

  confirmarMaterias() {
    this.subjectsClasses = [];

    this.subjects.forEach(subject => {
      if (subject.quantity > 0) {
        this.subjectsClasses.push({ name: subject.name, quantity: subject.quantity });
      }
    });
    this.visible = false
    console.log(this.subjectsClasses);
  }
}