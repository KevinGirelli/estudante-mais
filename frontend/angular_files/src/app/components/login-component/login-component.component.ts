import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login/login.service';
import { HttpClientModule, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { CheckboxModule } from 'primeng/checkbox';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { provideAnimations } from '@angular/platform-browser/animations';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    CheckboxModule,
    ToastModule,
    DialogModule,
    ButtonModule,
    InputTextModule
  ],
  providers: [LoginService, MessageService, provideAnimations()],
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.scss']
})
export class LoginComponentComponent {
  matricula!: string;
  password!: string;
  checked: boolean = false;
  visible: boolean = false;
  verificationCode: string[] = ['', '', '', '', '', ''];

  constructor(private loginService: LoginService, private router: Router, private messageService: MessageService) {}

  login() {
    const dados = {
      emailOrCode: this.matricula,
      password: this.password,
      keepMeLogged: this.checked
    };

    this.loginService.login(dados).subscribe(
      (res: HttpResponse<any>) => {
        if (res.status === 200) {
          // Recebe o token e guarda no localStorage
          localStorage.setItem("token", res.body.token);

          // se foi guardado corretamente, então redireciona
          if (localStorage.getItem("token")) {
            if (res.body.type === 100) {
              this.router.navigate(["admin"]);
            } else if (res.body.type === 0o10) {
              this.router.navigate(["student"]);
            } else if (res.body.type === 0o1) {
              this.router.navigate(["teacher"]);
            }
          }
        } else if (res.status === 400) {
          console.log("Credenciais inválidas");
          this.messageService.add({ severity: 'error', summary: 'Erro ao efetuar login', detail: 'Credenciais inválidas!' });
        } else if (res.status === 202) {
          this.visible = true;  // Exibe o modal de verificação de dois fatores
        }
      },
      (err: any) => {
        console.log(err);
        this.messageService.add({ severity: 'error', summary: 'Erro ao efetuar login', detail: 'Ocorreu um erro ao efetuar login!' });
      }
    );
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
          //Recebe o token e guarda no localStorage
          localStorage.setItem("token", data.token);
          
          //se foi guardado corretamente, então redireciona
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
        console.log("Código inválido");
        this.messageService.add({ severity: 'error', summary: 'Erro ao efetuar login', detail: 'Seu código é inválido!' });
      }

      if(res.status == 404){
        console.log("Código expirado")
        this.messageService.add({ severity: 'error', summary: 'Erro ao efetuar login', detail: 'Seu código está expirado!' });
      }
    })
  }

  clearVerificationCode() {
    this.verificationCode = ['', '', '', '', '', ''];
  }
}