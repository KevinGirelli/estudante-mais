import { NgClass } from '@angular/common';
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
  ],
  templateUrl: './dashboard-student.component.html',
  styleUrls: ['./dashboard-student.component.scss'],
  providers: [
    MessageService
  ]
})
export class DashboardStudentComponent implements OnInit {
  isMenuOpen = false;
  studentName: string = 'Kévin Girelli';

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
        console.log("REDIRECT");
        this.router.navigate(["403"]);
        return;
      }

      const notificationResponse = await fetch("http://localhost:8080/notification/getNotifications/" + localStorage.getItem("userID"), {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (notificationResponse.status === 200) {
        const notifications = await notificationResponse.json();
        notifications.forEach((notification: any) => {
          this.messageService.add({
            severity: notification.severity,
            summary: notification.summary,
            detail: notification.detail
          });
        });
      }

      const userResponse = await fetch("http://localhost:8080/user/getUser/" + localStorage.getItem("userID"), {
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });

      if (userResponse.status === 200) {
        const userData = await userResponse.json();
        this.studentName = userData.name;
      }
    } catch (error) {
      console.error("Failed to fetch", error);
    }
  }

  testNotification() {
    this.messageService.add({ severity: 'success', summary: 'Teste', detail: 'Teste bombando' });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
