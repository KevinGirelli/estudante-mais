import { Routes } from '@angular/router';
import { HomeComponentComponent } from './components/home-component/home-component.component';
import { LoginComponentComponent } from './components/login-component/login-component.component';
import { DashboardAdminComponent } from './components/dashboard-admin/dashboard-admin.component';
import { RegisterComponent } from './components/dashboard-admin/register/register.component';
import { DashboardStudentComponent } from './components/dashboard-student/dashboard-student.component';
import { DashboardTeacherComponent } from './components/dashboard-teacher/dashboard-teacher.component';
import { ClassesComponent } from './components/dashboard-admin/classes/classes.component';

export const routes: Routes = [
  {path: "", component: HomeComponentComponent},

  {path: "login", component: LoginComponentComponent},

  {path: "admin", component: DashboardAdminComponent},

  {path: "admin/register", component: RegisterComponent},

  {path: "admin/classes", component: ClassesComponent},

  {path: "teacher", component: DashboardTeacherComponent},

  {path: "student", component: DashboardStudentComponent}
];
