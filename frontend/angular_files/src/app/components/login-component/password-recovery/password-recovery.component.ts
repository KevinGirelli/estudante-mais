import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgIf } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-password-recovery',
  standalone: true,
  imports: [
    FormsModule, 
    InputTextModule, 
    ButtonModule, 
    NgIf,
    ToastModule
  ],
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss'],
  providers: [MessageService]
})
export class PasswordRecoveryComponent {
  constructor (private messageService: MessageService, private route: Router) {}

  email!: string;
  verificationCode: string[] = ['', '', '', '', '', ''];
  codeSent = false;
  passwordChange = false;
  newPassword!: string;
  confirmPassword!: string;

  async onSubmitEmail() {
    let dataToSend = {
      message: this.email
    }

    const response = await fetch("http://localhost:8080/passwordRecover/recoverPassword",{
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(dataToSend)
    })

    if(response.status == 400){
      this.messageService.add({ severity: 'erro', summary: 'Email inválido', detail: 'O email digitado não é válido'})
    }
    
    if(response.status == 200){
      this.messageService.add({ severity: 'info', summary: 'Email enviado.', detail: 'Um código de recuperação foi enviado ao seu email.'})
      this.codeSent = true;
    }
  }

  onInputChange(event: any, index: number) {
    const input = event.target as HTMLInputElement;
    const value = input.value;

    this.verificationCode[index] = value;

    if (value && index < this.verificationCode.length - 1) {
      const nextInput = input.nextElementSibling as HTMLInputElement | null;
      if (nextInput) {
        nextInput.focus();
      }
    }
  }

  async verifyCode() {
    let dataToSend = {
      message: this.verificationCode.join('')
    }
    const response = await fetch("http://localhost:8080/passwordRecover/verifyCode",{
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(dataToSend)
    })

    if(response.status == 200){
      this.passwordChange = true
    }

    if(response.status == 400){
      this.messageService.add({ severity: 'error', summary: 'Código inválido.', detail: 'O código fornecido está invalido'})
    }

    if(response.status == 404){
      this.messageService.add({ severity: 'error', summary: 'Código expirado.', detail: 'O código expirou, por favor tente novamente.'})
    }
  }

  async changePassword() {
    if (this.newPassword === this.confirmPassword) {
      let dataToSend = {
        message: this.verificationCode.join(''),
        secondMessage: this.newPassword
      } 
      const response = await fetch("http://localhost:8080/passwordRecover/updatePassword",{
        method: "POST",
        headers: {
          "Content-type": "application/json"
        },
        body: JSON.stringify(dataToSend)
      })
      
      if(response.status == 200){
        this.route.navigate(['login'])
      }

      if(response.status == 404){
        this.messageService.add({ severity: 'error', summary: 'Código expirado.', detail: 'O código expirou, por favor tente novamente.'})
      }
    } else {
      this.messageService.add({ severity: 'error', summary: 'Senhas não coincidem', detail: 'Digite a senha novamente.'})
    }
  }

  resendCode() {
    console.log('Reenviar código para:', this.email);
  }

  goBack() {
    if (this.passwordChange) {
      this.passwordChange = false;
      this.codeSent = true;
    } else {
      this.codeSent = false;
      this.verificationCode = ['', '', '', '', '', ''];
    }
  }
}
