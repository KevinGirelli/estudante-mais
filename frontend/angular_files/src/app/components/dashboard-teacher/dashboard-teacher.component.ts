import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
  
  constructor (private router: Router) {}

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
        this.router.navigate(["403"])
      }
    })
}
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
