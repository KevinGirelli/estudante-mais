import { Routes } from '@angular/router';
import { HomeComponentComponent } from './components/home-component/home-component.component';
import { LoginComponentComponent } from './components/login-component/login-component.component';

export const routes: Routes = [
  {
    path: "",
    component: HomeComponentComponent
  },

  {
    path: "login",
    component: LoginComponentComponent
  }
];
