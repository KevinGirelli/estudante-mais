import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';

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
    TableModule
  ],
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {
  isMenuOpen = false;
  classes: Class[] = [];

  constructor(private router: Router) {}

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
      }
    })

    const allClasses = localStorage.getItem("classes");

    if (allClasses) {
      const parsedData = JSON.parse(allClasses);
      const keys = Object.keys(parsedData);
      keys.forEach(key => {
        const value = parsedData[key];
        let addClass: Class = {
          classID: value.classID,
          className: value.className,
          gradeType: value.gradeType,
          gradeNumber: value.gradeNumber,
          classMonitor: value.classMonitor || "A definir"
        };
        this.classes.push(addClass);
      });
    }
  }

  navigateToStudents(classItem: Class) {
    this.router.navigate(['/admin/classes', classItem.className]);
  }  
}
