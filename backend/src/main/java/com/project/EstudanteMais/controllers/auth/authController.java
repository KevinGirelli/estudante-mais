package com.project.EstudanteMais.controllers.auth;

import com.project.EstudanteMais.Entity.UserRoles;
import com.project.EstudanteMais.Entity.dto.loginDTO;
import com.project.EstudanteMais.Entity.dto.registerStudentDTO;
import com.project.EstudanteMais.Entity.dto.registerTeacherDTO;
import com.project.EstudanteMais.Entity.dto.tokenDTO;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.infra.security.TokenService;
import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class authController {
  @Autowired
  private TokenService tokenService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private adminRepository adminRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity loginRequest(@RequestBody loginDTO loginData){
    var adminUser = adminRepository.findBydirectorEmail(loginData.emailOrCode());
    var studentUser = studentRepository.findBystudentEmailOrStudentRegistration(loginData.emailOrCode(), loginData.emailOrCode());
    var teacherUser = teacherRepository.findByteacherEmail(loginData.emailOrCode());

    if(adminUser != null){
      if(this.passwordEncoder.matches(loginData.password(),adminUser.getPassword()) == true){
        var usernamepassword = new UsernamePasswordAuthenticationToken(adminUser.getUsername(),null,adminUser.getAuthorities());
        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(adminUser);
        tokenDTO returnToken = new tokenDTO(token,100);

        return ResponseEntity.ok(returnToken);
      }else{
        return ResponseEntity.badRequest().body("Invalid credentials, please check the credentials.");
      }
    }

    if(studentUser != null){
      if(this.passwordEncoder.matches(loginData.password(),studentUser.getPassword()) == true){
        var usernamepassword = new UsernamePasswordAuthenticationToken(studentUser.getUsername(),null,studentUser.getAuthorities());

        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(studentUser);
        tokenDTO returnToken = new tokenDTO(token,010);

        return ResponseEntity.ok(returnToken);
      }else{
        return ResponseEntity.badRequest().body("Invalid credentials, please check the credentials.");
      }
    }

    if(teacherUser != null){
      if(this.passwordEncoder.matches(loginData.password(),teacherUser.getPassword()) == true){
        var usernamepassword = new UsernamePasswordAuthenticationToken(teacherUser.getUsername(),null,teacherUser.getAuthorities());

        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(teacherUser);
        tokenDTO returnToken = new tokenDTO(token,001);

        return ResponseEntity.ok(returnToken);
      }else{
        return ResponseEntity.badRequest().body("Invalid credentials, please check the credentials.");
      }
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/verifyStudentToken")
  public ResponseEntity studentTokenVerify(){
    return ResponseEntity.accepted().build();
  }
  @PostMapping("/verifyTeacherToken")
  public ResponseEntity teacherTokenVerify(){
    return ResponseEntity.accepted().build();
  }
  @PostMapping("/verifyAdminToken")
  public ResponseEntity adminTokenVerify(){
    return ResponseEntity.accepted().build();
  }
}
