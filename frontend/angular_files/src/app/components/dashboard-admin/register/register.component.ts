import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StepperModule } from 'primeng/stepper';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    StepperModule,
    ButtonModule,
    NgClass,
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  studentFullname!: string;
  studentEmail!: string;
  studentPassword!: string;
  studentCPF!: string;
  studentAge!: Date;
  classes!: string;

  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  cadastrarAluno() {
    const dados = {
      studentFullname: this.studentFullname,
      studentEmail: this.studentEmail,
      studentPassword: this.studentPassword,
      studentCPF: this.studentCPF,
      studentAge: this.studentAge,
      classes: this.classes
    };

    
  }
}
