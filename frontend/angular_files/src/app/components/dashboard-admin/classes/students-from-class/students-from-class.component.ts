import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';

interface Student {
  fullname: string;
  email: string;
  cpf: string;
  age: Date;
}

@Component({
  selector: 'app-students-from-class',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './students-from-class.component.html',
  styleUrls: ['./students-from-class.component.scss']
})
export class StudentsFromClassComponent implements OnInit {
  isMenuOpen = false;
  students: Student[] = [];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit() {
    this.students = [
      {
        fullname: 'Kévin Girelli',
        email: 'kevingirelli@example.com',
        cpf: '123.456.789-00',
        age: new Date(2001, 3, 20)
      },
      {
        fullname: 'Zaymã Kinsiona',
        email: 'zaymakinsiona@example.com',
        cpf: '987.654.321-00',
        age: new Date(2002, 5, 15)
      }
    ];
  }
}
