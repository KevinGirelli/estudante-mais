package com.project.EstudanteMais.controllers.auth;

import com.project.EstudanteMais.Entity.UserRoles;
import com.project.EstudanteMais.Entity.dto.loginDTO;
import com.project.EstudanteMais.Entity.dto.registerStudentDTO;
import com.project.EstudanteMais.Entity.dto.registerTeacherDTO;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.infra.security.TokenService;
import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
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

@RestController
@RequestMapping("/auth")
public class authController {
  @Autowired
  private TokenService tokenService;

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

        return ResponseEntity.ok(token);
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

        return ResponseEntity.ok(token);
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

        return ResponseEntity.ok(token);
      }else{
        return ResponseEntity.badRequest().body("Invalid credentials, please check the credentials.");
      }
    }
    return ResponseEntity.notFound().build();
  }

}
