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
import { StudentEditComponent } from './components/dashboard-admin/classes/students-from-class/student-edit/student-edit.component';
import { Error403Component } from './components/error403/error403.component';
import { AssessmentsComponent } from './components/dashboard-teacher/assessments/assessments.component';
import { CreateAssessmentComponent } from './components/dashboard-teacher/assessments/create-assessment/create-assessment.component';
import { ListAssessmentComponent } from './components/dashboard-teacher/assessments/list-assessment/list-assessment.component';
import { MyAssessmentsComponent } from './components/dashboard-student/my-assessments/my-assessments.component';
import { AttendanceComponent } from './components/dashboard-teacher/attendance/attendance.component';
import { EditAssessmentComponent } from './components/dashboard-teacher/assessments/list-assessment/edit-assessment/edit-assessment.component';
import { MyGradesComponent } from './components/dashboard-student/my-grades/my-grades.component';
import { SchoolYearConfigComponent } from './components/dashboard-admin/school-year-config/school-year-config.component';
import { ScheduleComponent } from './components/dashboard-admin/schedule/schedule.component';
import { ChatComponent } from './components/chat/chat.component';
import { PasswordRecoveryComponent } from './components/login-component/password-recovery/password-recovery.component';
import { StudentScheduleComponent } from './components/dashboard-student/student-schedule/student-schedule.component';
import { TeacherScheduleComponent } from './components/dashboard-teacher/teacher-schedule/teacher-schedule.component';
import { AuthTwoFactorComponent } from './components/login-component/auth-two-factor/auth-two-factor.component';

export const routes: Routes = [
  { path: "403", component: Error403Component },

  { path: "", component: HomeComponentComponent },

  { path: "login", component: LoginComponentComponent },

  { path: "passwordRecovery", component: PasswordRecoveryComponent },

  { path: "admin", component: DashboardAdminComponent },

  { path: "admin/register", component: RegisterComponent },

  { path: "admin/classes", component: ClassesComponent },

  { path: "admin/class/students/:classStudent", component: StudentsFromClassComponent },

  { path: "admin/class/students/:classStudent/:student", component: StudentEditComponent },

  { path: "admin/class/teacher/:classTeacher", component: TeachersFromClassComponent },

  { path: "admin/teachers", component: TeachersComponent },

  { path: "admin/teachers/:editTeacher", component: TeacherEditComponent },

  { path: "admin/schedule", component: ScheduleComponent },

  { path: "admin/school/year/config",  component: SchoolYearConfigComponent },

  { path: "teacher", component: DashboardTeacherComponent },

  { path: "teacher/assessments", component: AssessmentsComponent },

  { path: "teacher/assessments/createAssessment", component: CreateAssessmentComponent },
  
  { path: "teacher/assessments/listAssessment", component: ListAssessmentComponent },
  
  { path: "teacher/assessments/edit", component: EditAssessmentComponent },
  
  { path: "teacher/attendance", component: AttendanceComponent },

  { path: "teacher/schedule", component: TeacherScheduleComponent },

  { path: "student", component: DashboardStudentComponent },

  { path: "student/assessments", component: MyAssessmentsComponent },

  { path: "student/grades", component: MyGradesComponent },

  { path: "student/schedule", component: StudentScheduleComponent },

  { path: "chat/:chatUser", component: ChatComponent },

  { path: "auth2fa/:email/:type", component: AuthTwoFactorComponent }
];
