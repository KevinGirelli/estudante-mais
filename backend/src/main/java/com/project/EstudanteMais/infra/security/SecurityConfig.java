package com.project.EstudanteMais.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.UUID;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  internalFilter internalFilter;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
    return httpSecurity.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/twoStepVerify").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/sendActiviateTwoStepMail").hasAnyAuthority("TEACHER","STUDENT")
                    .requestMatchers(HttpMethod.POST, "/auth/activateTwoStep").hasAnyAuthority("TEACHER","STUDENT")
                    .requestMatchers(HttpMethod.POST,"/auth/verifyAdminToken").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/auth/verifyStudentToken").hasAuthority("STUDENT")
                    .requestMatchers(HttpMethod.POST,"/auth/verifyTeacherToken").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.POST,"/admin/registerStudent").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerTeacher").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerSubject").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/attendence/getAllStudentsFromClass").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.PATCH, "/admin/studentDataManager/updateStudentPrimaryData").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.PATCH,"/admin/teacherDataManager/updateTeacherPrimaryData").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/classesDataManager/getAllStudentsFromClass").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/classesDataManager/getSearchAllClassesRelatedToSubject").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/admin/dataManager/getStudent").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/assess/getAssessmentFromTeacher").hasAnyAuthority("TEACHER","ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/getTeacher").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/attendence/registryAttendence").hasAnyAuthority("TEACHER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/student/getAssessments").hasAnyAuthority("STUDENT","ADMIN")
                    .requestMatchers(HttpMethod.GET, "/notification/getNotifications").hasAnyAuthority("STUDENT","TEACHER")
                    .requestMatchers(HttpMethod.POST, "/notification/readNotification").hasAnyAuthority("STUDENT","TEACHER")
                    .requestMatchers(HttpMethod.GET, "/teacher/getAllStudentsFromClass").hasAnyAuthority("TEACHER","ADMIN")
                    .requestMatchers(HttpMethod.POST, "/assess/updateAssessment").hasAnyAuthority("TEACHER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/assess/postStudentGrade").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.POST, "/CrisAssistant/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/CrisAssistant/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/grades/viewGrades").hasAuthority("STUDENT")
                    .requestMatchers(HttpMethod.POST, "/student/viewSchedule").hasAuthority("STUDENT")
                    .requestMatchers(HttpMethod.POST,"/admin/registerQuarterType").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/grades/getCurrentType").hasAnyAuthority("TEACHER","ADMIN","STUDENT")
                    .requestMatchers(HttpMethod.POST,"/admin/schedule/genSchedule").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/schedule/getSchedule").hasAuthority("ADMIN")
                    .requestMatchers("/ws/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/teacher/getTeacherSchedule").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.POST,"/passwordRecover/updatePassword").permitAll()
                    .requestMatchers(HttpMethod.POST, "/passwordRecover/recoverPassword").permitAll()
                    .requestMatchers(HttpMethod.POST, "/passwordRecover/verifyCode").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/admin/schedule/deleteSchedule").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/schedule/setScheduleSettings").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/getMessageHistory").hasAnyAuthority("TEACHER","STUDENT")
                    .requestMatchers(HttpMethod.GET, "/teacher/getAllClassesFromTeacher").hasAnyAuthority("TEACHER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/teacherDataManager/getAllTeacherFromClass").hasAnyAuthority("ADMIN", "STUDENT")
                    .requestMatchers(HttpMethod.POST, "/assess/createNewAssessment").hasAnyAuthority("TEACHER","ADMIN")
                    .requestMatchers(HttpMethod.GET, "/teacher/getTeacherSubject").hasAnyAuthority("TEACHER","ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerClass").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/classDiary/registerNewDay").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.POST, "/classDiary/editClassDiary").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.GET,"/classDiary/getClassDiary").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.DELETE, "/classDiary/deleteClassDiary").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.GET, "/admin/schedule/getScheduleSettings").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/absense/getAbsenseFromSubject").hasAuthority("STUDENT")
                    .requestMatchers(HttpMethod.GET, "admin/subjectDataManager/getSubjects").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "admin/classesDataManager/getClassesAsync").hasAnyAuthority("ADMIN","TEACHER")
                    .anyRequest().authenticated())
            .addFilterBefore(internalFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
 public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
 }
}
