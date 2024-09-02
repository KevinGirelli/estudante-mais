import { NgClass, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-dashboard-student',
  standalone: true,
  imports: [
    NgClass,
    ToastModule,
    NgIf
  ],
  templateUrl: './dashboard-student.component.html',
  styleUrls: ['./dashboard-student.component.scss'],
  providers: [
    MessageService
  ]
})
export class DashboardStudentComponent implements OnInit {
  isMenuOpen = false;
  studentName: any = localStorage.getItem("username");
  isTwoFactorAuthenticated = false;

  constructor(private router: Router, private messageService: MessageService) {}

  async ngOnInit(): Promise<void> {
    try {
      const verifyResponse = await fetch("http://localhost:8080/auth/verifyStudentToken", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });
    
      if (verifyResponse.status === 403) {
        this.router.navigate(["403"]);
      }

      const notificationResponse = await fetch("http://localhost:8080/notification/getNotifications/" + localStorage.getItem("userID"), {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (notificationResponse.status === 200) {
        const notifications = await notificationResponse.json();
        notifications.forEach(async (notification: any) => {
          this.messageService.add({
            severity: "info",
            summary: notification.name
          });
          
          await fetch("http://localhost:8080/notification/readNotification/" + notification.notificationID,{
            method: "POST",
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token")
            }
          });
        });
      }
    } catch (error) {
      console.error("Failed to fetch", error);
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  checkUser(){
    this.router.navigate(["/chat/student"]);
  }

  async activateTwoStep(){
    const response = await fetch("http://localhost:8080/auth/sendActiviateTwoStepMail/" + localStorage.getItem("userEmail"),{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    })

    if(response.status == 404){
      this.messageService.add({ severity: 'info', summary: 'Verificação ativada', detail: 'Sua verificação de dois fatores já esta ativada.' })
    }

    if(response.status == 200){
      this.router.navigate(["auth2fa/" + localStorage.getItem("userEmail") + "/1"]);
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigate(["login"]);
  }
}
