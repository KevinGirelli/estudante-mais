import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';

interface Class {
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
    const allClasses = localStorage.getItem("classes");

    if (allClasses) {
      const parsedData = JSON.parse(allClasses);
      const keys = Object.keys(parsedData);
      keys.forEach(key => {
        const value = parsedData[key];
        let addClass: Class = {
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
