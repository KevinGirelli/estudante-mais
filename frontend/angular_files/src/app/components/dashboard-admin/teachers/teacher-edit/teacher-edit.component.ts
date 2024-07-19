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
  subjectID: string,
  name: string;
}

interface TeacherClass {
  classID: string;
  subjectID: string;
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

  teacherClasses: TeacherClass[] = [];

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
    let subjects: string[] = []
    
    fetch("http://localhost:8080/admin/subjectDataManager/getSubjects",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
      }
      
      if(res.status == 200){
        res.json().then(data =>{
            let keys = Object.keys(data)
            for(let i = 0; i <= keys.length-1; i++){
              const addSubject: Subject = {
                subjectID: data[i].subjectID,
                name: data[i].subjectname,
              }
              this.subjects.push(addSubject)

              if(teacher.subjects.includes(data[i].subjectname)){
                subjects.push(data[i].subjectID)
                this.selectedSubjects.push(addSubject)
              }
            }
        })
      }
    })

  
    fetch("http://localhost:8080/admin/classesDataManager/getSearchAllClassesRelatedToSubject",{
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      },
      body: JSON.stringify(subjects)
    }).then(res =>{
      if(res.status == 403){
        console.log("redirect")
      }

      if(res.status == 200){
        res.json().then(data =>{
          let keys = Object.keys(data)
            for(let i = 0; i <= keys.length-1; i++){
              const addTeacherClass: TeacherClass = {
                classID: data[i].classID,
                subjectID: data[i].subjectID,
                className: data[i].className,
                subjectName: data[i].subjectName,
                quantity: data[i].quantity
              }
              this.teacherClasses.push(addTeacherClass)
            }
        })
      }
    })

    
    if (teacher) {
      this.teacherName = teacher.teacherName;
      this.teacherEmail = teacher.teacherEmail;
      this.teacherPassword = teacher.teacherPassword;
      this.teacherCPF = teacher.teacherCPF;
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