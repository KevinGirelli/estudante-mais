import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login/login.service';
import { HttpClientModule, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { CheckboxModule } from 'primeng/checkbox';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    CheckboxModule,
    ToastModule
  ],
  providers: [LoginService, MessageService],
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.scss']
})
export class LoginComponentComponent {
  matricula!: String;
  password!: String;
  checked: boolean = false;

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
          this.router.navigate(["two"]);
        }
      },
      (err: any) => {
        console.log(err);
        this.messageService.add({ severity: 'error', summary: 'Erro ao efetuar login', detail: 'Ocorreu um erro ao efetuar login!' });
      }
    );
  }
}
