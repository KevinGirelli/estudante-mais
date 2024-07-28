import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-password-recovery',
  standalone: true,
  imports: [
    FormsModule, 
    InputTextModule, 
    ButtonModule, 
    NgIf
  ],
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss']
})
export class PasswordRecoveryComponent {
  email!: string;
  verificationCode: string[] = ['', '', '', '', '', ''];
  codeSent = false;
  passwordChange = false;
  newPassword!: string;
  confirmPassword!: string;

  onSubmitEmail() {
    if (this.email) {
      console.log(`Recuperação de senha para: ${this.email}`);
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

  verifyCode() {
    const code = this.verificationCode.join('');
    console.log(`Código inserido: ${code}`);
    if (code.length === 6) {
      this.passwordChange = true;
      this.codeSent = false;
    } else {
      console.log('Código inválido');
    }
  }

  changePassword() {
    if (this.newPassword === this.confirmPassword) {
      console.log(`Nova senha: ${this.newPassword}`);
    } else {
      console.log('As senhas não coincidem');
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
