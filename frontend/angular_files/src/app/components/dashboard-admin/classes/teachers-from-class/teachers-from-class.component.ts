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
  
  constructor(private router: Router, private dataSaverService: DataSaverService) {}

  async ngOnInit(): Promise<void> {
    try {
      const response = await fetch("", {
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
          this.subjects = data;
        });
      }
    } catch (error) {
      console.log(error);
    }
    
    this.loadAvailableTeachers();
  }

  async loadAvailableTeachers() {
    try {
      const response = await fetch("", {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (response.status == 200) {
        response.json().then(data => {
          this.availableTeachers = data;
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  openAddTeacherModal(subject: Subject) {
    this.subjectToEdit = subject;
    this.isAddTeacherModalOpen = true;
  }

  closeAddTeacherModal() {
    this.isAddTeacherModalOpen = false;
    this.selectedTeacher = undefined;
  }

  async assignTeacher() {
    if (this.subjectToEdit && this.selectedTeacher) {
      await fetch(``, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify({ teacherID: this.selectedTeacher.teacherID })
      });

      this.subjectToEdit.teacher = this.selectedTeacher;
      this.closeAddTeacherModal();
    }
  }

  async removeTeacher(subject: Subject) {
    await fetch(``, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    });

    subject.teacher = undefined;
  }

  logout() {
    
  }
}
