import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';

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

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit() {
    const allClasses = localStorage.getItem("classes");
    
    if(allClasses){
      const parsedData= JSON.parse(allClasses)
    
      const keys = Object.keys(parsedData);
      let index = 0
      keys.forEach(key => {
        const value = parsedData[key];
        if(value.classMonitor == undefined){
          let addClass: Class = {
            className: value.className,
            gradeType: value.gradeType,
            gradeNumber: value.gradeNumber,
            classMonitor: "A definir"
          }
          this.classes.push(addClass);
        }else{
          let addClass: Class = {
            className: value.className,
            gradeType: value.gradeType,
            gradeNumber: value.gradeNumber,
            classMonitor: value.classMonitor
          }
          this.classes.push(addClass);
        }
      });
    };
  }
}
