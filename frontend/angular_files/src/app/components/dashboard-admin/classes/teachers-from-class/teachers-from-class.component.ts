import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ListboxModule } from 'primeng/listbox';
import { FormsModule } from '@angular/forms';
import { DataSaverService } from '../../../../services/tempDataSaver/data-saver.service';

interface Subject {
  id: string;
  name: string;
  teacher?: Teacher;
}

interface Teacher {
  teacherID: string;
  teacherName: string;
  teacherEmail: string;
  teacherCPF: string;
}

@Component({
  selector: 'app-subjects',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    DialogModule,
    ListboxModule,
    FormsModule
  ],
  templateUrl: './teachers-from-class.component.html',
  styleUrls: ['./teachers-from-class.component.scss']
})
export class TeachersFromClassComponent implements OnInit {
  isMenuOpen = false;
  subjects: Subject[] = [];
  availableTeachers: Teacher[] = [];
  selectedTeacher?: Teacher;
  isAddTeacherModalOpen = false;
  subjectToEdit?: Subject;
  classID: any;
  
  constructor(private router: Router, private dataSaverService: DataSaverService) {}

  async ngOnInit(): Promise<void> {
    try {
      this.classID = this.dataSaverService.getData()
      const response = await fetch("http://localhost:8080/admin/classesDataManager/getClassSubjectTeacher/" + this.classID, {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (response.status == 403) {
        this.router.navigate(["403"]);
      }

      if (response.status == 200) {
        response.json().then(data => {
          for(let i = 0; i <= data.length; i++){
            if(data[i].teacherID == "0"){
              let addSubject: Subject = {
                id: data[i].subjectID,
                name: data[i].subjectName
             }

             this.subjects.push(addSubject)
            }else{
              let addTeacher: Teacher = {
                teacherID: data[i].teacherID,
                teacherName: data[i].teacherName,
                teacherEmail: "0",
                teacherCPF: "0"
              }

              let addSubject: Subject = {
                 id: data[i].subjectID,
                 name: data[i].subjectName,
                 teacher: addTeacher
              }

              this.subjects.push(addSubject)
            }
          }

        });
      }
    } catch (error) {
      console.log(error);
    }
     
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  async openAddTeacherModal(subject: Subject) {
    this.subjectToEdit = subject;
    this.isAddTeacherModalOpen = true;
    
    try {
      const response = await fetch("http://localhost:8080/admin/teacherDataManager/getTeachersFromSubject/" + this.subjectToEdit.id + "/" + this.classID, {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });
      if (response.status == 200) {
        response.json().then(data => {
          console.log(data)
          for(let i = 0 ; i <= data.length; i++){
            let addTeacher: Teacher = {
              teacherID: data[i].teacherID,
              teacherName: data[i].teacherName,
              teacherCPF: "",
              teacherEmail: ""
            }

            this.availableTeachers.push(addTeacher)
          }
        });
      }
    } catch (error) {
      console.log(error);
    }

  }

  closeAddTeacherModal() {
    this.isAddTeacherModalOpen = false;
    this.selectedTeacher = undefined;
    this.availableTeachers = []
  }

  async assignTeacher() {
    if (this.subjectToEdit && this.selectedTeacher) {
      await fetch(`http://localhost:8080/admin/classesDataManager/assignTeacherToClass` + "/" + this.selectedTeacher.teacherID + 
        "/" + this.classID + "/" + this.subjectToEdit.id, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      }).then(response =>{
        if(response.status == 200){
          this.subjects.forEach(s =>{
            if(s.id == this.subjectToEdit?.id){
              s.teacher = this.selectedTeacher
            }
          })
        }else{
          console.log("Error on assign teacher")
        }
      });

      this.subjectToEdit.teacher = this.selectedTeacher;
      this.closeAddTeacherModal();
    }
  }

  async removeTeacher(subject: Subject) {
    await fetch(`http://localhost:8080/admin/classesDataManager/unAssignTeacher`+ "/" + subject.teacher?.teacherID + 
      "/" + this.classID + "/" + subject.id, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(response =>{
      if(response.status == 200){
        this.subjects.forEach(s =>{
          if(s.id == subject.id){
            s.teacher = undefined
          }
        })
      }
    });

    subject.teacher = undefined;
  }

  logout() {
    
  }
}
