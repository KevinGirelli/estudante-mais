import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-teacher',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './dashboard-teacher.component.html',
  styleUrl: './dashboard-teacher.component.scss'
})
export class DashboardTeacherComponent implements OnInit {
  isMenuOpen = false;

  ngOnInit(): void {
    fetch("http://localhost:8080/auth/verifyTeacherToken",{
      method: "POST",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      if(res.status == 403){
        //redirecionar para pagina de não autorizado.
        console.log("REDIRECT")
      }
    })
}
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
