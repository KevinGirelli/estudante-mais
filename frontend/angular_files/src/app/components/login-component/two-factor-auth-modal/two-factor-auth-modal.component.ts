import { Component } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { provideAnimations } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-two-factor-auth-modal',
  standalone: true,
  imports: [
    DialogModule,
    ButtonModule,
    InputTextModule,
    FormsModule
  ],
  providers: [
    provideAnimations()
  ],
  templateUrl: './two-factor-auth-modal.component.html',
  styleUrls: ['./two-factor-auth-modal.component.scss']
})
export class TwoFactorAuthModalComponent {
  visible: boolean = false;
  verificationCode: string[] = ['', '', '', '', '', ''];

  showDialog() {
    this.visible = true;
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

    console.log('Verifying code:', code);

    this.clearVerificationCode();

    this.visible = false;

  }
  clearVerificationCode() {
    this.verificationCode = ['', '', '', '', '', ''];
  }
}