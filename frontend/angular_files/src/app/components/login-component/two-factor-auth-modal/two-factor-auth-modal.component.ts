import { Component, OnInit } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { provideAnimations } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

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
export class TwoFactorAuthModalComponent implements OnInit {
  constructor(private router: Router) {};

  visible: boolean = false;
  verificationCode: string[] = ['', '', '', '', '', ''];

  ngOnInit(): void {
    this.visible = true
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
    const data = {
      code: code
    };

    fetch("http://localhost:8080/auth/twoStepVerify", {
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status == 202){
        res.json().then(data =>{
          //Recebe o token e guarda no cash(localstorage)
          localStorage.setItem("token", data.token);
          
          //se foi guardado corretamente, entao redireciona
          if(localStorage.getItem("token")){
            if(data.type == 100){
              this.router.navigate(["admin"]);
            }
            if(data.type == 0o10){
              this.router.navigate(["student"]);
            }
            if(data.type == 0o1){
              this.router.navigate(["teacher"]);
            }
          }
        })
      }

      if(res.status == 400){
        console.log("Codígo invalido");
      }

      if(res.status == 404){
        console.log("ERRO: codigo nao encontrado")
      }
    })

  }
  clearVerificationCode() {
    this.verificationCode = ['', '', '', '', '', ''];
  }
}