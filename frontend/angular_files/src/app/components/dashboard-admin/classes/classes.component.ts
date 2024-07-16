import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';

interface Class {
  className: string;
  gradeType: string;
  gradeNumber: number[];
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
    this.classes = [
      {
        className: '351',
        gradeType: 'Ensino médio',
        gradeNumber: [3],
        classMonitor: 'Kévin'
      },
      {
        className: '352',
        gradeType: 'Ensino médio',
        gradeNumber: [3],
        classMonitor: 'Zaymã'
      }
    ];
  }
}
