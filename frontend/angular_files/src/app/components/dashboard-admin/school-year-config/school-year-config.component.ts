import { Component } from '@angular/core';
import { CalendarModule } from 'primeng/calendar';
import { ButtonModule } from 'primeng/button';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';

@Component({
  selector: 'app-school-year-config',
  standalone: true,
  imports: [
    CalendarModule, 
    ButtonModule, 
    InputTextareaModule, 
    DropdownModule, 
    FormsModule,
    NgClass,
    NgIf
  ],
  templateUrl: './school-year-config.component.html',
  styleUrls: ['./school-year-config.component.scss']
})
export class SchoolYearConfigComponent {
  isMenuOpen = false;

  startDate: Date | null = null;
  endDate: Date | null = null;
  endBimester1: Date | null = null;
  endBimester2: Date | null = null;
  endBimester3: Date | null = null;
  endBimester4: Date | null = null;
  endTrimester1: Date | null = null;
  endTrimester2: Date | null = null;
  endTrimester3: Date | null = null;
  holidays: string = '';
  selectedYearType: string | null = null;
  yearTypes = [
    { label: 'Bimestral', value: 2 },
    { label: 'Trimestral', value: 3},
  ];

  toggleMenu() {
    this.isMenuOpen = true;
  }

  async saveConfig() {
   const response = await fetch("http://localhost:8080/admin/registerQuarterType/" + this.selectedYearType,{
    method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
   })

   if(response.status == 200){
    console.log("sucesso ao salvar")
   }
  }

  logout() {
    
  }
}
