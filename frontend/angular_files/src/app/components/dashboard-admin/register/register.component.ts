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
  selecionarPeriodosVisible: boolean = false;

  subjectPeriod: String = ""
  allClasses: Class[] = [];
  subjects: Subject[] = [];
  subjectsClasses: Subject[] = [];
  teacherAllSubject: string[] = [];
  classeAllSubjects: string[] = [];
  subjectName: string = ''
  maxGrades: String = ""

  teacherPeriod: String = ""
  teacherPeriods: String[] = ["Matutino", "Vespertino", "Integral", "Noturno"]

  classPeriod: string = '';
  periods: String[] = [];

  allPeriods: String[] = ["Matutino","Vespertino","Integral","Noturno", "Matutino + Noturno", "Vespertino + Noturno", "Integral + Noturno"];
  totalClasses: number = 0
  maxClasses: number = 0

  ngOnInit(): void {
    fetch("http://localhost:8080/admin/subjectDataManager/getSubjects",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
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
        this.router.navigate(["403"])
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

    fetch("http://localhost:8080/admin/classesDataManager/getPeriodType",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        this.router.navigate(["403"])
      }

      if(res.status == 200){
        res.text().then(data =>{
          let toInt = parseInt(data)
            if(toInt > 3){
                if(toInt == 4){
                  this.maxClasses = 50
                  this.periods.push(this.allPeriods[0])
                  this.periods.push(this.allPeriods[3])
                }

                if(toInt == 5){
                  this.maxClasses = 50
                  this.periods.push(this.allPeriods[1])
                  this.periods.push(this.allPeriods[3])
                }

                if(toInt == 6){
                  this.maxClasses = 50
                  this.periods.push(this.allPeriods[0])
                  this.periods.push(this.allPeriods[1])
                  this.periods.push(this.allPeriods[3])
                }
            }else{
              if(toInt == 0){
                this.maxClasses = 25
                this.periods.push(this.allPeriods[0])
              }

              if(toInt == 1){
                this.maxClasses = 25
                this.periods.push(this.allPeriods[1])
              }

              if(toInt == 2){
                this.maxClasses = 50
                this.periods.push(this.allPeriods[0])
                this.periods.push(this.allPeriods[1])
              }

              if(toInt == 3){
                this.maxClasses = 25
                this.periods.push(this.allPeriods[3])
              }
            }
        })
      }
    })
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
  teacherWorkingDays: string = '';
  monday: string = '';
  tuesday: string = '';
  wednesday: string = '';
  thursday: string = '';
  friday: string = '';

  // Cadastro de Turmas
  className: string = '';
  gradeType: string = '';
  gradeNumber: number[] = [];
  classMonitor: string = '';
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

  selecionarPeriodos() {
    this.selecionarPeriodosVisible = true;
  }
  
  confirmarPeriodos() {
      this.setWorkingDays()
      console.log(this.teacherWorkingDays)
      this.selecionarPeriodosVisible = false
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
      phoneNumber: this.phoneNumberTeacher,
      teacherWorkingDays: this.teacherWorkingDays
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
      subjects: this.classeAllSubjects,
      type: this.periods.indexOf(this.classPeriod)
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
    if(this.classPeriod == ""){
      this.messageService.add({ severity: 'info', summary: 'Selecione o periodo de aula', detail: 'Selecione um periodo de aula para prosseguir.' });
    }else{
      this.visible = true;
      this.classeAllSubjects = []
    }
  }

  increaseQuantity(subject: Subject) {
    if(this.totalClasses+1 <= this.maxClasses){
      subject.quantity++;
      this.totalClasses++;
    }else{
      if(this.totalClasses == 50){
        this.messageService.add({ severity: 'info', summary: 'Adição Bloqueada', detail: 'Limite de aulas por semana atingido (50)'});
      }else{
        this.messageService.add({ severity: 'info', summary: 'Adição Bloqueada', detail: 'O periodo selecionado permite apenas um limite de ' + this.maxClasses + " aulas por semana, modifique o periodo para integral caso seja necessário um maior limite" });
      }
    }
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
      let response = fetch("http://localhost:8080/admin/registerSubject/" + this.subjectName + "/" + this.subjectPeriod + "/" + this.maxGrades,{
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

  setWorkingDays(){
    this.teacherWorkingDays = ''
    this.teacherWorkingDays += this.teacherPeriods.indexOf(this.monday)
    this.teacherWorkingDays += this.teacherPeriods.indexOf(this.tuesday)
    this.teacherWorkingDays += this.teacherPeriods.indexOf(this.wednesday)
    this.teacherWorkingDays += this.teacherPeriods.indexOf(this.thursday)
    this.teacherWorkingDays += this.teacherPeriods.indexOf(this.friday)
  }
}