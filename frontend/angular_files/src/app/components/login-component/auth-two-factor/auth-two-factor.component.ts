import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { NgIf } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-auth-two-factor',
  standalone: true,
  imports: [FormsModule, InputTextModule, ButtonModule, NgIf,ToastModule],
  templateUrl: './auth-two-factor.component.html',
  styleUrls: ['./auth-two-factor.component.scss'],
  providers: [MessageService]
})
export class AuthTwoFactorComponent implements OnInit {
  constructor(private routes: Router, private router: ActivatedRoute,private messageService: MessageService) {}

  email!: any;
  verificationCode: string[] = ['', '', '', '', '', ''];
  codeSent = false;
  verified = false;
  activateType: any;

  ngOnInit() {
    this.router.paramMap.subscribe(params => {
      this.email  = params.get('email')?.toString();
      this.activateType = params.get("type")
      this.codeSent = true
    });

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
    const code = this.verificationCode.join('');
    if (code.length === 6) {
      if(this.activateType == "1"){
        const dataToSend = {
          message: code
        }
        
        const response = await fetch("http://localhost:8080/passwordRecover/verifyCode",{
          method: "POST",
          headers: {
            "Content-type": "application/json"
          },
          body: JSON.stringify(dataToSend)
        })

        if(response.status == 200){
          const response2 = await fetch("http://localhost:8080/auth/activateTwoStep/" + localStorage.getItem("userID"),{
            method: "POST",
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token")
            }
          })

          if(response2.status == 200){
            response2.json().then(data =>{
              console.log(data)
              if(data.message == "s"){
                this.routes.navigate(['student'])
              }

              if(data.message == "t"){
                this.routes.navigate(['teacher'])
              }
            })
          }
        }
    
        if(response.status == 400){
          this.messageService.add({ severity: 'error', summary: 'Código inválido.', detail: 'O código fornecido está invalido'})
        }
    
        if(response.status == 404){
          this.messageService.add({ severity: 'error', summary: 'Código expirado.', detail: 'O código expirou, por favor tente novamente.'})
        }
      }else{
        let dataToSend = {
          code: code
        }
        const response = await fetch("http://localhost:8080/auth/twoStepVerify",{
          method: "POST",
          headers: {
            "Content-type": "application/json"
          },
          body: JSON.stringify(dataToSend)
        })
    
        if(response.status == 202){
          response.json().then(data =>{
            //Recebe o token e guarda no localStorage
            localStorage.setItem("token", data.token);
            
            //se foi guardado corretamente, então redireciona
            if(localStorage.getItem("token")){
              if(data.type == 100){
                this.routes.navigate(["admin"]);
              }
              if(data.type == 0o10){
                localStorage.setItem("username", data.username)
                localStorage.setItem("userID", data.userID)
                localStorage.setItem("classID", data.classID)
                localStorage.setItem("userEmail", data.email)
                this.routes.navigate(["student"]);
              }
              if(data.type == 0o1){
                localStorage.setItem("username", data.username)
                localStorage.setItem("userID", data.userID)
                localStorage.setItem("userEmail", data.email)
                this.routes.navigate(["teacher"]);
              }
            }
          })
        }
    
        if(response.status == 400){
          this.messageService.add({ severity: 'error', summary: 'Código inválido.', detail: 'O código fornecido está invalido'})
        }
    
        if(response.status == 404){
          this.messageService.add({ severity: 'error', summary: 'Código expirado.', detail: 'O código expirou, por favor tente novamente.'})
        }
      }
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
