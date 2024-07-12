import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';

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
      }
    })
    
    //Pegar turmas do banco
    fetch("http://localhost:8080/admin/classesDataManager/getClassesAsync",{
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(res => {
      console.log(res.status)
      if(res.status == 200){
        res.json().then(data => {
          localStorage.setItem("classes", data)
          console.log("Classes data loaded sucessfuly.")
        })
      }
    })
}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
