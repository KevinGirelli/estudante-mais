import { Component } from '@angular/core';
import { CalendarModule } from 'primeng/calendar';
import { ButtonModule } from 'primeng/button';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

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
    NgIf,
    ToastModule
  ],
  templateUrl: './school-year-config.component.html',
  styleUrls: ['./school-year-config.component.scss'],
  providers: [MessageService]
})


export class SchoolYearConfigComponent {

  constructor (private messageService: MessageService) {}

  isMenuOpen = false;

  generationType: number = 25;
  types = [
    { label: 'Meio-Período', value: 25 },
    { label: 'Período Integral', value: 50},
  ];
  consecType: number = 1
  consecTypes = [1,2,3,4,5,6,7,8,9,10]

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
  selectedPeriodType: String | null = null;
  periodTypes = [
    { label: 'Matutino', value: 0 },
    { label: 'Vespertino', value: 1},
    { label: 'Integral', value: 2 },
    { label: 'Noturno', value: 3 },
    { label: 'Matutino + Noturno', value: 4 },
    { label: 'Vespertino + Noturno', value: 5 },
    { label: 'Integral + Noturno', value: 6 },
  ]

  toggleMenu() {
    this.isMenuOpen = true;
  }

  async saveConfig() {
    console.log(this.selectedPeriodType)
   const response = await fetch("http://localhost:8080/admin/registerQuarterType/" + this.selectedYearType,{
    method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
   })

   const periodType = await fetch("http://localhost:8080/admin/classesDataManager/setPeriodType/" + this.selectedPeriodType,{
    method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
   })

   if(periodType.status == 200){
    this.messageService.add({ severity: 'success', summary: 'Periodo de aulas salvo com sucesso.', detail: 'Configurações Salvas' })
   }

   if(response.status == 200){
    this.messageService.add({ severity: 'success', summary: 'Configurações do ano letivo alteradas', detail: 'Configurações Salvas' })
   }

   if(this.generationType == 25){
    const response2 = await fetch("http://localhost:8080/admin/schedule/setScheduleSettings/"
      + this.generationType + "," + "5" + "," + this.consecType
    ,{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response2.status == 200){
      this.messageService.add({ severity: 'success', summary: 'Configurações de geração de horários alteradas.', detail: 'Configurações Salvas' })
    }
   }
   
   if(this.generationType == 50){
    const response2 = await fetch("http://localhost:8080/admin/schedule/setScheduleSettings/"
      + this.generationType + "," + "10" + "," + this.consecType
    ,{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response2.status == 200){
      this.messageService.add({ severity: 'success', summary: 'Configurações de geração de horários alteradas.', detail: 'Configurações Salvas' })
    }
   }
   
  }

  logout() {
    
  }
}
