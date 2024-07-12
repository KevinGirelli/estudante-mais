import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-student',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './dashboard-student.component.html',
  styleUrl: './dashboard-student.component.scss'
})
export class DashboardStudentComponent implements OnInit {
  isMenuOpen = false;

  ngOnInit(): void {
      fetch("http://localhost:8080/auth/verifyStudentToken",{
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
