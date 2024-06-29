import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule
  ],
  providers: [LoginService],
  templateUrl: './login-component.component.html',
  styleUrl: './login-component.component.scss'
})
export class LoginComponentComponent {
  matricula!: String;
  password!: String;

  constructor(private loginService: LoginService) {}

  login(){
    const dados = {
      emailOrCode: this.matricula,
      password: this.password
    }

    this.loginService.login(dados).subscribe(
      (res: any) => {
        console.log('Login realizado com sucesso: ', res);
        this.matricula = '';
        this.password = '';
      },
      (err: any) => {
        console.error('Erro ao efetuar o login: ', err)
      }
    )
  }
}
