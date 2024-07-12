import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login/login.service';
import { HttpClientModule, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { DashboardAdminComponent } from '../dashboard-admin/dashboard-admin.component';

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

  constructor(private loginService: LoginService,private router: Router) {};
  

  login(){
    const dados = {
      emailOrCode: this.matricula,
      password: this.password
    }

    this.loginService.login(dados).subscribe(
      (res: any) => {
        //Recebe o token e guarda no cash(localstorage)
        localStorage.setItem("token", res.token);
        
        //se foi guardado corretamente, entao redireciona
        if(localStorage.getItem("token")){
          if(res.type == 100){
            this.router.navigate(["admin"]);
          }
          if(res.type == 0o10){
            this.router.navigate(["student"]);
          }
          if(res.type == 0o1){
            this.router.navigate(["teacher"]);
          }
        }
      },
      (err: any) => {
        console.log(err);
      }
    )
  }
}
