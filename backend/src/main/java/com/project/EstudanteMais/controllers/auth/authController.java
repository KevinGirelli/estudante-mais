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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
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

  @Autowired
  private ResourceLoader resourceLoader;

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

          Resource resource = resourceLoader.getResource("classpath:static/mail.html");
          String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
          htmlContent = htmlContent.replace("BigDog","Admin")
                  .replace("value=1","value=" + codeToAdd.charAt(0))
                  .replace("value=2","value=" + codeToAdd.charAt(1))
                  .replace("value=3","value=" + codeToAdd.charAt(2))
                  .replace("value=4","value=" + codeToAdd.charAt(3))
                  .replace("value=5","value=" + codeToAdd.charAt(4))
                  .replace("value=6","value=" + codeToAdd.charAt(5));

          this.adminRepository.updateTwoStepCode(codeToAdd,UUID.fromString(this.uuiDformatter.formatUuid(adminUser.getAdminID())));
          this.emailService.sendHtmlEmail("Codigo de autenticacao",adminUser.getDirectorEmail(),htmlContent);
          return ResponseEntity.status(HttpStatus.CONTINUE).build();
        }

        var usernamepassword = new UsernamePasswordAuthenticationToken(adminUser.getUsername(),null,adminUser.getAuthorities());
        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(adminUser);
        tokenDTO returnToken = new tokenDTO(token,100,"",adminUser.getUsername());

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
          System.out.println(codeToAdd);

          Resource resource = resourceLoader.getResource("classpath:static/mail.html");
          String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
          htmlContent = htmlContent.replace("BigDog",studentUser.getStudentFullname())
                  .replace("value=a","value=" + codeToAdd.charAt(0))
                  .replace("value=b","value=" + codeToAdd.charAt(1))
                  .replace("value=c","value=" + codeToAdd.charAt(2))
                  .replace("value=d","value=" + codeToAdd.charAt(3))
                  .replace("value=e","value=" + codeToAdd.charAt(4))
                  .replace("value=f","value=" + codeToAdd.charAt(5));



          this.studentRepository.updateTwoStepCode(codeToAdd,UUID.fromString(this.uuiDformatter.formatUuid(studentUser.getStudentID())));
          this.emailService.sendHtmlEmail("Codigo de autenticacao",studentUser.getUsername(),htmlContent);

          return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        var usernamepassword = new UsernamePasswordAuthenticationToken(studentUser.getUsername(),null,studentUser.getAuthorities());

        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(studentUser);
        tokenDTO returnToken = new tokenDTO(token,010,studentUser.getStudentID().toString(),studentUser.getStudentFullname());

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

          Resource resource = resourceLoader.getResource("classpath:static/mail.html");
          String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
          htmlContent = htmlContent.replace("BigDog",teacherUser.getTeacherName())
                  .replace("value=1","value=" + codeToAdd.charAt(0))
                  .replace("value=2","value=" + codeToAdd.charAt(1))
                  .replace("value=3","value=" + codeToAdd.charAt(2))
                  .replace("value=4","value=" + codeToAdd.charAt(3))
                  .replace("value=5","value=" + codeToAdd.charAt(4))
                  .replace("value=6","value=" + codeToAdd.charAt(5));

          this.teacherRepository.updateTwoStepCode(codeToAdd,UUID.fromString(this.uuiDformatter.formatUuid(teacherUser.getTeacherID())));
          this.emailService.sendHtmlEmail("Codigo de autenticacão",teacherUser.getTeacherName(),htmlContent);

          return ResponseEntity.status(HttpStatus.CONTINUE).build();
        }

        var usernamepassword = new UsernamePasswordAuthenticationToken(teacherUser.getUsername(),null,teacherUser.getAuthorities());

        Authentication auth = usernamepassword;
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken(teacherUser);
        tokenDTO returnToken = new tokenDTO(token,001,teacherUser.getTeacherID().toString(),teacherUser.getTeacherName());

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
          tokenDTO returnToken = new tokenDTO(token,100,"",adminUser.getUsername());

          return ResponseEntity.accepted().body(returnToken);
        }
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    if(studentUser != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.code())){
        if(studentUser.getTwoStepCode().equals(code.code())){
          var usernamepassword = new UsernamePasswordAuthenticationToken(studentUser.getUsername(),null,studentUser.getAuthorities());
          Authentication auth = usernamepassword;
          SecurityContextHolder.getContext().setAuthentication(auth);
          var token = this.tokenService.GenerateToken(studentUser);
          tokenDTO returnToken = new tokenDTO(token,010,studentUser.getStudentID().toString(),studentUser.getStudentFullname());

          return ResponseEntity.accepted().body(returnToken);
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
          tokenDTO returnToken = new tokenDTO(token,001,teacherUser.getTeacherID().toString(),teacherUser.getTeacherName());

          return ResponseEntity.accepted().body(returnToken);
        }
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.badRequest().build();
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
