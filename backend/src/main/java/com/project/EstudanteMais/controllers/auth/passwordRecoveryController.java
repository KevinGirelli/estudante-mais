package com.project.EstudanteMais.controllers.auth;

import com.project.EstudanteMais.Entity.dto.MessageDTO;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.services.RandomCodeService;
import com.project.EstudanteMais.services.RegisterCodeTimerService;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/passwordRecover")
public class passwordRecoveryController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  studentRepository studentRepository;
  @Autowired
  RandomCodeService randomCodeService;

  @Autowired
  RegisterCodeTimerService registerCodeTimer;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

  @PostMapping("/recoverPassword")
  public ResponseEntity recoverPassword(@RequestBody MessageDTO emailToRecover) throws IOException {
    var getTeacher = (teacher) this.teacherRepository.findByteacherEmail(emailToRecover.message());
    var getStudent = (student) this.studentRepository.findBystudentEmailOrStudentRegistration(emailToRecover.message(),emailToRecover.message());

    if(getTeacher == null && getStudent == null){
      return ResponseEntity.badRequest().build();
    }

    if(getTeacher != null){
      var code = this.randomCodeService.GenerateCode();
      code = this.randomCodeService.verifyCode(code);
      this.registerCodeTimer.StartTRegisterTimer(code);
      this.teacherRepository.updateTwoStepCode(code,getTeacher.getTeacherID());

      Resource resource = resourceLoader.getResource("classpath:static/mail.html");
      String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
      htmlContent = htmlContent.replace("BigDog",getTeacher.getTeacherName())
              .replace("value=a","value=" + code.charAt(0))
              .replace("value=b","value=" + code.charAt(1))
              .replace("value=c","value=" + code.charAt(2))
              .replace("value=d","value=" + code.charAt(3))
              .replace("value=e","value=" + code.charAt(4))
              .replace("value=f","value=" + code.charAt(5))
              .replace("seu código de autenticação:", "Seu código de redefinição de senha");

      this.emailService.sendHtmlEmail("Código de redefinição de senha",emailToRecover.message(),htmlContent);
      return ResponseEntity.ok().build();
    }

    if(getStudent != null){
      var code = this.randomCodeService.GenerateCode();
      code = this.randomCodeService.verifyCode(code);
      this.registerCodeTimer.StartTRegisterTimer(code);
      this.studentRepository.updateTwoStepCode(code,getStudent.getStudentID());

      Resource resource = resourceLoader.getResource("classpath:static/mail.html");
      String htmlContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));
      htmlContent = htmlContent.replace("BigDog",getStudent.getStudentFullname())
              .replace("value=a","value=" + code.charAt(0))
              .replace("value=b","value=" + code.charAt(1))
              .replace("value=c","value=" + code.charAt(2))
              .replace("value=d","value=" + code.charAt(3))
              .replace("value=e","value=" + code.charAt(4))
              .replace("value=f","value=" + code.charAt(5))
              .replace("seu código de autenticação:", "Seu código de redefinição de senha");

      this.emailService.sendHtmlEmail("Código de redefinição de senha",emailToRecover.message(),htmlContent);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/verifyCode")
  public ResponseEntity verifyCode(@RequestBody MessageDTO code){
    var studentCode = this.studentRepository.findBytwoStepCode(code.message());
    var teacherCode = this.teacherRepository.findBytwoStepCode(code.message());

    if(studentCode == null && teacherCode == null){
      return ResponseEntity.badRequest().build();
    }

    if(studentCode != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.message())){
        return ResponseEntity.ok().build();
      }else{
        return ResponseEntity.notFound().build();
      }
    }

    if(teacherCode != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.message())){
        return ResponseEntity.ok().build();
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/updatePassword")
  public ResponseEntity updatePassword(@RequestBody MessageDTO code){
    var newPassword = code.secondMessage();
    var getStudent = (student) this.studentRepository.findBytwoStepCode(code.message());
    var getTeacher = (teacher) this.teacherRepository.findBytwoStepCode(code.message());

    if(getStudent != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.message())){
        String encodedPassword = this.passwordEncoder.encode(newPassword);
        this.studentRepository.updateStudentPassword(encodedPassword, getStudent.getStudentID());
        return ResponseEntity.ok().build();
      }else{
        return ResponseEntity.notFound().build();
      }
    }

    if(getTeacher != null){
      if(this.configPreferencesService.getActiveCodes().contains(code.message())){
        String encodedPassword = this.passwordEncoder.encode(newPassword);
        this.teacherRepository.updateTeacherPassword(encodedPassword,getTeacher.getTeacherID());
        return ResponseEntity.ok().build();
      }else{
        return ResponseEntity.notFound().build();
      }
    }

    return ResponseEntity.internalServerError().build();
  }
}
