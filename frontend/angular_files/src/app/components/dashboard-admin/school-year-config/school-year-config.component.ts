import { Component, OnInit } from '@angular/core';
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


export class SchoolYearConfigComponent implements OnInit {

  constructor (private messageService: MessageService) {}
 
  isMenuOpen = false;

  generationType: number = 25;
  types = [
    { label: 'Meio-Período', value: 25 },
    { label: 'Período Integral', value: 50},
  ];
  consecType: number = 1
  consecTypes = [1,2,3,4,5,6,7,8,9,10]

  boletimDateBi: Date | null = null;
  boletimDateTri: Date | null = null;
  boletimDateSem: Date | null = null;
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

  formatarDataParaInput(dateString: string): string {
    const [dia, mes, ano] = dateString.split("/");
    return `${ano}-${mes.padStart(2, "0")}-${dia.padStart(2, "0")}`;
  }

  criarDataLocal(dateString: string): Date {
    const [ano, mes, dia] = dateString.split("-").map(Number);
    return new Date(ano, mes - 1, dia);
  }



  async ngOnInit(): Promise<void>  {
    const getSettings = await fetch("http://localhost:8080/admin/schedule/getSchoolConfig",{
      method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
     })

     if(getSettings.status == 200){
      getSettings.json().then(data =>{
        console.log(data)
        const bi = this.formatarDataParaInput(data.biDate)
        const tri  = this.formatarDataParaInput(data.triDate)
        const sem = this.formatarDataParaInput(data.semDate)

        this.boletimDateBi = this.criarDataLocal(bi);
        this.boletimDateTri = this.criarDataLocal(tri);
        this.boletimDateSem = this.criarDataLocal(sem);

      
        this.selectedPeriodType = data.schoolPeriod
        this.consecType = data.maxConsecutiveClasses
      })
     }
  
  }


  toggleMenu() {
    this.isMenuOpen = true;
  }

  async saveConfig() {
    console.log(this.selectedPeriodType)
    let formatedBoletimDateBi = this.boletimDateBi?.toLocaleDateString("pt-BR")
    let formatedBoletimDateTri = this.boletimDateTri?.toLocaleDateString("pt-BR")
    let formatedBoletimDateSem = this.boletimDateSem?.toLocaleDateString("pt-BR")

    formatedBoletimDateBi = formatedBoletimDateBi?.replace(/\//g,"-")
    formatedBoletimDateTri = formatedBoletimDateTri?.replace(/\//g,"-")
    formatedBoletimDateSem = formatedBoletimDateSem?.replace(/\//g,"-")
   
   const periodType = await fetch("http://localhost:8080/admin/classesDataManager/setPeriodType/" + this.selectedPeriodType,{
    method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
   })

   if(periodType.status == 200){
    this.messageService.add({ severity: 'success', summary: 'Periodo de aulas salvo com sucesso.', detail: 'Configurações Salvas' })
   }

  
   if(this.generationType == 25){
    console.log
    const response2 = await fetch("http://localhost:8080/admin/schedule/setScheduleSettings/"
      + this.generationType + "," + "5" + "," + this.consecType + "/" + formatedBoletimDateBi + "/" + formatedBoletimDateTri
      + "/" + formatedBoletimDateSem
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
