import { Component } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { ListboxModule } from 'primeng/listbox';
import { NgForOf, NgIf } from '@angular/common';

interface Absence {
  label: string;
  date: string;
  count: number;
}

@Component({
  selector: 'app-my-absences',
  standalone: true,
  imports: [
    DropdownModule, 
    FormsModule, 
    ListboxModule, 
    NgForOf, 
    NgIf
  ],
  templateUrl: './my-absences.component.html',
  styleUrls: ['./my-absences.component.scss']
})
export class MyAbsencesComponent{
  subjects: { label: string, value: string }[] = [];
  selectedSubject!: string;

  periods: { label: string, value: number }[] = [];
  selectedPeriod!: string;

  absences: Absence[] = [];
  totalAbsences: number = 0;

  allAbsences: { [key: string]: { [key: string]: Absence[] } } = {};

  onSelectChange() {

  }
}
