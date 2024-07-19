import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './dashboard-admin.component.html',
  styleUrl: './dashboard-admin.component.scss'
})
export class DashboardAdminComponent implements OnInit {
  isMenuOpen = false;

  constructor (private router: Router) {}

  ngOnInit(): void {
    //verificar token
    fetch("http://localhost:8080/auth/verifyAdminToken",{
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
