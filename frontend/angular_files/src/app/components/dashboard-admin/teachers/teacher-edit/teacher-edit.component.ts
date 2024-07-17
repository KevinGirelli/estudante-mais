import { NgClass } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-teacher-edit',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './teacher-edit.component.html',
  styleUrl: './teacher-edit.component.scss'
})
export class TeacherEditComponent {
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
