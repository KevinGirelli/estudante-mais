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
    { label: 'Bimestral', value: 'Bimestral' },
    { label: 'Trimestral', value: 'Trimestral' }
  ];

  toggleMenu() {
    this.isMenuOpen = true;
  }

  saveConfig() {
    console.log('Configuração salva:', {
      startDate: this.startDate,
      endDate: this.endDate,
      holidays: this.holidays,
      yearType: this.selectedYearType,
      endBimester1: this.endBimester1,
      endBimester2: this.endBimester2,
      endBimester3: this.endBimester3,
      endBimester4: this.endBimester4,
      endTrimester1: this.endTrimester1,
      endTrimester2: this.endTrimester2,
      endTrimester3: this.endTrimester3
    });
  }
}
