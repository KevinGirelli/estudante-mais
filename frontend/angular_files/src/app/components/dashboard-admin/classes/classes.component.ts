import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';
import { DialogModule } from 'primeng/dialog';
import { provideAnimations } from '@angular/platform-browser/animations';
import { DataSaverService } from '../../../services/tempDataSaver/data-saver.service';

interface Class {
  classID: string
  className: string;
  gradeType: string;
  gradeNumber: number;
  classMonitor: string;
}

@Component({
  selector: 'app-classes',
  standalone: true,
  imports: [
    NgClass,
    TableModule,
    DialogModule
  ],
  providers: [provideAnimations()],
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {
  isMenuOpen = false;
  classes: Class[] = [];
  visible: boolean = false;
  selectedClass: Class | null = null;

  constructor(private router: Router, private datasaver: DataSaverService) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit() {
    fetch("http://localhost:8080/auth/verifyAdminToken",{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
        this.router.navigate(["403"])
      }
    })

    fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res =>{
     res.json().then(data =>{
      for(let i = 0; i <= data.length-1;i++){
        let addClass: Class = {
          classID: data[i].classID,
          className: data[i].className,
          gradeType: data[i].gradeType,
          gradeNumber: data[i].gradeNumber,
          classMonitor: data[i].classMonitor || "A definir"
        }
        this.classes.push(addClass)
      }
     })
    })
  }

  openModalSwitch(selectedClass: Class) {
    this.selectedClass = selectedClass;
    this.visible = true;
  }  

  navigateToStudents() {
    if (this.selectedClass) {
      this.datasaver.setData(this.selectedClass.classID)
      this.router.navigate(['admin/class/students', this.selectedClass.className]);
    }
  }
  
  navigateToTeachers() {
    if (this.selectedClass) {
      this.datasaver.setData(this.selectedClass.classID)
      this.router.navigate(['admin/class/teacher', this.selectedClass.classID]);
    }
  }

  logout() {
    
  }
  
}