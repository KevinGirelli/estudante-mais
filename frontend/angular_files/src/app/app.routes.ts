import { Routes } from '@angular/router';
import { HomeComponentComponent } from './components/home-component/home-component.component';
import { LoginComponentComponent } from './components/login-component/login-component.component';
import { DashboardAdminComponent } from './components/dashboard-admin/dashboard-admin.component';

export const routes: Routes = [
  {
    path: "",
    component: HomeComponentComponent
  },

  {
    path: "login",
    component: LoginComponentComponent
  },

  {
    path: "admin",
    component: DashboardAdminComponent
  }
];
