import { Component, OnInit } from '@angular/core';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ListboxModule } from 'primeng/listbox';

interface Subject {
  subjectID: string;
  name: string;
  quantity: number;
}

@Component({
  selector: 'app-edit-class',
  standalone: true,
  imports: [
    FormsModule, 
    NgClass, 
    ToastModule, 
    NgFor, 
    NgIf, 
    DialogModule,
    ListboxModule
    
  ],
  providers: [MessageService],
  templateUrl: './edit-class.component.html',
  styleUrls: ['./edit-class.component.scss']
})
export class EditClassComponent implements OnInit {
  isMenuOpen = false;
  confirmEditVisible = false;
  visibleSubjects = false;
  cadastroMateriasVisible = false;

  className = '';
  gradeType = '';
  gradeNumber: number[] = [];
  classPeriod = '';
  periods: string[] = [];
  gradeNumbers: number[] = [];
  subjects: Subject[] = [];
  subjectsClasses: Subject[] = [];
  subjectName = '';

  constructor(private messageService: MessageService) {}

  ngOnInit() {
    this.initializePeriods();
  }

  initializePeriods() {
    this.periods = ['Matutino', 'Vespertino', 'Noturno'];
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  openConfirmEditDialog() {
    this.confirmEditVisible = true;
  }

  closeConfirmEditDialog() {
    this.confirmEditVisible = false;
  }

  selecionarMateria() {
    this.visibleSubjects = true;
  }

  confirmarMaterias() {
    this.visibleSubjects = false;
    this.messageService.add({ severity: 'success', summary: 'Matérias Selecionadas', detail: 'As matérias foram confirmadas com sucesso.' });
  }

  cadastrarMateria() {
  }

  abrirCadastroMateria() {
    this.cadastroMateriasVisible = true;
  }

  editarClasse() {
    this.confirmEditVisible = false;
    this.messageService.add({ severity: 'success', summary: 'Turma Editada', detail: 'A turma foi editada com sucesso.' });
  }

  updateGradeNumbers() {
    switch (this.gradeType) {
      case 'Fundamental 1':
        this.gradeNumbers = [1, 2, 3, 4, 5];
        break;
      case 'Fundamental 2':
        this.gradeNumbers = [6, 7, 8, 9];
        break;
      case 'Ensino Médio':
        this.gradeNumbers = [1, 2, 3];
        break;
      default:
        this.gradeNumbers = [];
    }
  }

  increaseQuantity(subject: Subject) {
    subject.quantity++;
  }

  decreaseQuantity(subject: Subject) {
    if (subject.quantity > 0) {
      subject.quantity--;
    }
  }

  logout() {
    
  }

  deleteClass() {

  }

  back() {

  }
}
