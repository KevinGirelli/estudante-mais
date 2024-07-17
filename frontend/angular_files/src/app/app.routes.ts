import { Routes } from '@angular/router';
import { HomeComponentComponent } from './components/home-component/home-component.component';
import { LoginComponentComponent } from './components/login-component/login-component.component';
import { DashboardAdminComponent } from './components/dashboard-admin/dashboard-admin.component';
import { RegisterComponent } from './components/dashboard-admin/register/register.component';
import { DashboardStudentComponent } from './components/dashboard-student/dashboard-student.component';
import { DashboardTeacherComponent } from './components/dashboard-teacher/dashboard-teacher.component';
import { ClassesComponent } from './components/dashboard-admin/classes/classes.component';
import { StudentsFromClassComponent } from './components/dashboard-admin/classes/students-from-class/students-from-class.component';
import { TeachersFromClassComponent } from './components/dashboard-admin/classes/teachers-from-class/teachers-from-class.component';
import { TeachersComponent } from './components/dashboard-admin/teachers/teachers.component';
import { TeacherEditComponent } from './components/dashboard-admin/teachers/teacher-edit/teacher-edit.component';

export const routes: Routes = [
  {path: "", component: HomeComponentComponent},

  {path: "login", component: LoginComponentComponent},

  {path: "admin", component: DashboardAdminComponent},

  {path: "admin/register", component: RegisterComponent},

  {path: "admin/classes", component: ClassesComponent},

  {path: "admin/class/students/:classStudent", component: StudentsFromClassComponent},

  {path: "admin/class/teacher/:classTeacher", component: TeachersFromClassComponent},

  {path: "admin/teachers", component: TeachersComponent},

  {path: "admin/teachers/:editTeacher", component: TeacherEditComponent},

  {path: "teacher", component: DashboardTeacherComponent},

  {path: "student", component: DashboardStudentComponent},

];
