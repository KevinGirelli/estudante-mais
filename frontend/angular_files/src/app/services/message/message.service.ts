import { Injectable } from '@angular/core';
import { MessageService as PrimeNgMessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private primengMessageService: PrimeNgMessageService) {}

  addSuccess(summary: string, detail: string) {
    this.primengMessageService.add({ severity: 'success', summary, detail });
  }

  addError(summary: string, detail: string) {
    this.primengMessageService.add({ severity: 'error', summary, detail });
  }

  addInfo(summary: string, detail: string) {
    this.primengMessageService.add({ severity: 'info', summary, detail });
  }

  addWarn(summary: string, detail: string) {
    this.primengMessageService.add({ severity: 'warn', summary, detail });
  }

  clear() {
    this.primengMessageService.clear();
  }
}
