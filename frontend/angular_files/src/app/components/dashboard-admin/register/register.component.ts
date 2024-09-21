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
import { Router } from '@angular/router';

interface Subject {
  subjectID: string
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
  cadastroMateriasVisible: boolean = false;

  allClasses: Class[] = [];
  subjects: Subject[] = [];
  subjectsClasses: Subject[] = [];
  teacherAllSubject: string[] = [];
  classeAllSubjects: string[] = [];
  subjectName: string = '';


  ngOnInit(): void {
    fetch("http://localhost:8080/admin/subjectDataManager/getSubjects",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
        this.router.navigate(["403"])
      }

      if(res.status == 200){
        res.json().then(data =>{
            let keys = Object.keys(data)
            for(let i = 0; i <= keys.length-1; i++){
              const addSubject: Subject = {
                subjectID: data[i].subjectID,
                name: data[i].subjectname,
                quantity: 0
              }
              this.subjects.push(addSubject)
            }
        })
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
          this.allClasses = []
          for(let i = 0; i <= data.length-1; i++){
            const add: Class = {
              id: data[i].classID,
              name: data[i].className
            }
            this.allClasses.push(add)
          }
        })
      }
    })
    this.subjects = [];
  }

  // Cadastro Alunos
  Fullname: string = '';
  email: string = '';
  password: string = '';
  cpf: string = '';
  age: Date = new Date('0000-00-00');
  classID: string = '';
  phoneNumberStudent: string = '';

  // Cadastro de Professores
  teacherName: string = '';
  teacherEmail: string = '';
  teacherPassword: string = '';
  teacherCPF: string = '';
  selectedSubjects: Subject[] = [];
  phoneNumberTeacher: string = '';

  // Cadastro de Turmas
  className: string = '';
  gradeType: string = '';
  gradeNumber: number[] = [];
  classMonitor: string = '';
  classPeriod: string = '';

  periods: string[] = [];
  gradeNumbers: number[] = [];

  constructor(private router: Router ,private registerService: RegisterService, private messageService: MessageService) {}

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
      classID: this.classesSelected,
      phoneNumber: this.phoneNumberStudent
    };
    console.log(studentData)

    this.registerService.registerStudent(studentData).subscribe(response => {
      console.log('Aluno registrado:', response);
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Aluno registrado com sucesso!' });
      this.clearAlunoForm();
      this.ngOnInit()
    }, error => {
      console.error('Erro ao registrar aluno:', error);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar aluno!' });
    });
  }

  cadastrarProfessor() {
    this.selectedSubjects.forEach(subject =>{
      this.teacherAllSubject.push(subject.subjectID)
    })
    
    const teacherData = {
      teacherEmail: this.teacherEmail,
      teacherPassword: this.teacherPassword,
      teacherName: this.teacherName,
      teacherCPF: this.teacherCPF,
      subjects: this.teacherAllSubject,
      phoneNumber: this.phoneNumberTeacher
    };


    this.registerService.registerTeacher(teacherData).subscribe(response => {
      console.log('Professor registrado:', response);
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Professor registrado com sucesso!' });
      this.clearProfessorForm();
      this.ngOnInit()
    }, error => {
      console.error('Erro ao registrar professor:', error);
      this.messageService.add({ severity: 'error', summary: 'Erro durante o cadastro', detail: 'Erro ao registrar professor!' });
    });
  }

  cadastrarTurma() {
    this.subjectsClasses.forEach(subject =>{
      let stringFormat = subject.subjectID + "/" + subject.quantity
      this.classeAllSubjects.push(stringFormat)
    })
    const classData = {
      className: this.className,
      gradeType: this.gradeType,
      gradeNumber: this.gradeNumber,
      subjects: this.classeAllSubjects
    };


    this.registerService.registerClass(classData).subscribe(response => {
      console.log('Turma registrada:', response);
      this.messageService.add({ severity: 'success', summary: 'Cadastro concluído', detail: 'Turma registrada com sucesso!' });
      this.clearTurmaForm();
      this.ngOnInit();
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
    this.classeAllSubjects = []
  }

  increaseQuantity(subject: Subject) {
    subject.quantity++;
  }

  decreaseQuantity(subject: Subject) {
    if (subject.quantity > 0) {
      subject.quantity--;
    }
  }

  abrirCadastroMateria(){
    this.cadastroMateriasVisible = true
  }

  cadastrarMateria() {
    if(this.subjectName != ""){
      let response = fetch("http://localhost:8080/admin/registerSubject/" + this.subjectName,{
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      })

      response.then(res =>{
        if(res.status == 200){
          res.text().then(text =>{
            console.log(text)
            this.subjects.push({subjectID: text, name: this.subjectName, quantity: 0})
          })
        }
      })
      this.cadastroMateriasVisible = false;
    }
  }

  confirmarMaterias() {
    this.subjectsClasses = [];         

    this.subjects.forEach(subject => {
      if (subject.quantity > 0) {
        this.subjectsClasses.push({subjectID: subject.subjectID, name: subject.name, quantity: subject.quantity });
      }
    });
    this.visible = false
  }

  logout() {
    
  }
}