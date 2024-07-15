package com.project.EstudanteMais.controllers.auth;

import com.project.EstudanteMais.Entity.dto.loginDTO;
import com.project.EstudanteMais.Entity.dto.tokenDTO;
import com.project.EstudanteMais.Entity.dto.twoFactorCodeDTO;
import com.project.EstudanteMais.Entity.schoolAdmin;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.infra.security.TokenService;
import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.services.RandomCodeService;
import com.project.EstudanteMais.services.RegisterCodeTimerService;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  RandomCodeService randomCodeService;

  @Autowired
  RegisterCodeTimerService registerCodeTimer;

  @Autowired
  UUIDformatter uuiDformatter;

  @PostMapping("/login")
  public ResponseEntity loginRequest(@RequestBody loginDTO loginData) throws Exception {
    var adminUser = (schoolAdmin) adminRepository.findBydirectorEmail(loginData.emailOrCode());
    var studentUser = (student) studentRepository.findBystudentEmailOrStudentRegistration(loginData.emailOrCode(), loginData.emailOrCode());
    var teacherUser = (teacher) teacherRepository.findByteacherEmail(loginData.emailOrCode());

    if(adminUser != null){
      if(this.passwordEncoder.matches(loginData.password(),adminUser.getPassword()) == true){
        if(adminUser.getTwostepverification() == true){
          String code = this.randomCodeService.GenerateCode();
          String codeToAdd = this.randomCodeService.verifyCode(code);
          this.registerCodeTimer.StartTRegisterTimer(codeToAdd);

          adminUser.setTwoStepCode(codeToAdd);
          this.emailService.sendSimpleMailMessage("Codigo de autenticacao",adminUser.getDirectorEmail(),UUID.randomUUID().toString(),codeToAdd);
          return ResponseEntity.status(HttpStatus.CONTINUE).build();
        }

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
        if(studentUser.getTwostepverification() == true){
          String code = this.randomCodeService.GenerateCode();
          String codeToAdd = this.randomCodeService.verifyCode(code);
          this.registerCodeTimer.StartTRegisterTimer(codeToAdd);

          this.studentRepository.updateTwoStepCode(codeToAdd,UUID.fromString(this.uuiDformatter.formatUuid(studentUser.getStudentID())));
          this.emailService.sendSimpleMailMessage("Codigo de autenticacao",studentUser.getUsername(),UUID.randomUUID().toString(),codeToAdd);

          return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

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
        if(teacherUser.getTwostepverification() == true){
          String code = this.randomCodeService.GenerateCode();
          String codeToAdd = this.randomCodeService.verifyCode(code);
          this.registerCodeTimer.StartTRegisterTimer(codeToAdd);

          teacherUser.setTwoStepCode(codeToAdd);
          this.emailService.sendSimpleMailMessage("Codigo de autenticacao",teacherUser.getUsername(),UUID.randomUUID().toString(),codeToAdd);
          System.out.println("CHEGOU");
          return ResponseEntity.status(HttpStatus.CONTINUE).build();
        }

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


  @PostMapping("/twoStepVerify")
  public ResponseEntity twoStepVerification(@RequestBody twoFactorCodeDTO code){

    var adminUser = (schoolAdmin) adminRepository.findBytwoStepCode(code.code());
    var studentUser = (student) studentRepository.findBytwoStepCode(code.code());
    var teacherUser = (teacher) teacherRepository.findBytwoStepCode(code.code());

    if(adminUser != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.code())){
        if(adminUser.getTwoStepCode() == code.code()){
          var usernamepassword = new UsernamePasswordAuthenticationToken(adminUser.getUsername(),null,adminUser.getAuthorities());
          Authentication auth = usernamepassword;
          SecurityContextHolder.getContext().setAuthentication(auth);
          var token = this.tokenService.GenerateToken(adminUser);
          tokenDTO returnToken = new tokenDTO(token,100);

          return ResponseEntity.accepted().body(returnToken);
        }else{
          return ResponseEntity.badRequest().build();
        }
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    if(studentUser != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.code())){
        System.out.println("STUDNET CODE: " + studentUser.getTwoStepCode());
        System.out.println("CODE SENDED: " + code.code());
        if(studentUser.getTwoStepCode().equals(code.code())){
          var usernamepassword = new UsernamePasswordAuthenticationToken(studentUser.getUsername(),null,studentUser.getAuthorities());
          Authentication auth = usernamepassword;
          SecurityContextHolder.getContext().setAuthentication(auth);
          var token = this.tokenService.GenerateToken(studentUser);
          tokenDTO returnToken = new tokenDTO(token,010);

          return ResponseEntity.accepted().body(returnToken);
        }else{
          return ResponseEntity.badRequest().build();
        }
      }else{
        return ResponseEntity.notFound().build();
      }
    }

    if(teacherUser != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.code())){
        if(teacherUser.getTwoStepCode() == code.code()){
          var usernamepassword = new UsernamePasswordAuthenticationToken(teacherUser.getUsername(),null,teacherUser.getAuthorities());
          Authentication auth = usernamepassword;
          SecurityContextHolder.getContext().setAuthentication(auth);
          var token = this.tokenService.GenerateToken(teacherUser);
          tokenDTO returnToken = new tokenDTO(token,001);

          return ResponseEntity.accepted().body(returnToken);
        }else{
          return ResponseEntity.badRequest().build();
        }
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.internalServerError().build();
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
