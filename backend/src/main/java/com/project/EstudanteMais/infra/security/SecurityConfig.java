package com.project.EstudanteMais.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                    .requestMatchers(HttpMethod.POST,"/auth/verifyAdminToken").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST,"/auth/verifyStudentToken").hasAuthority("STUDENT")
                    .requestMatchers(HttpMethod.POST,"/auth/verifyTeacherToken").hasAuthority("TEACHER")
                    .requestMatchers(HttpMethod.POST,"/admin/registerStudent").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerTeacher").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerSubject").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/admin/studentDataManager/updateStudentPrimaryData").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/classesDataManager/getAllStudentsFromClass").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/classesDataManager/getSearchAllClassesRelatedToSubject").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/admin/dataManager/getStudent").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/getTeacher").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/registerClass").hasAuthority("ADMIN")
                    .anyRequest().authenticated())
            .addFilterBefore(internalFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
 public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
 }
}
