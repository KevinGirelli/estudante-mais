import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-auth-two-factor',
  standalone: true,
  imports: [FormsModule, InputTextModule, ButtonModule, NgIf],
  templateUrl: './auth-two-factor.component.html',
  styleUrls: ['./auth-two-factor.component.scss']
})
export class AuthTwoFactorComponent implements OnInit {
  email!: string;
  verificationCode: string[] = ['', '', '', '', '', ''];
  codeSent = false;
  verified = false;

  ngOnInit() {
    this.getEmailFromBackend();
  }

  getEmailFromBackend() {
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
      this.verified = true;
      this.codeSent = false;
    } else {
      console.log('Código inválido');
    }
  }

  resendCode() {
    console.log('Reenviar código para:', this.email);
  }

  goBack() {
    if (this.verified) {
      this.verified = false;
      this.codeSent = true;
    } else {
      this.codeSent = false;
      this.verificationCode = ['', '', '', '', '', ''];
    }
  }
}
