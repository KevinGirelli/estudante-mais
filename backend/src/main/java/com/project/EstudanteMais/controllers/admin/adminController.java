package com.project.EstudanteMais.controllers.admin;

import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.Entity.dto.registerStudentDTO;
import com.project.EstudanteMais.Entity.dto.registerTeacherDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configService;
import com.project.EstudanteMais.services.genRegistrationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class adminController {

  @Autowired
  private adminRepository adminRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  configService configService;

  @Autowired
  genRegistrationCodeService genRegistrationCodeService;

  @PostMapping("/registerStudent")
  public ResponseEntity registerNewStudent(@RequestBody registerStudentDTO registerStudent){
    UserDetails studentAlreadyExist = studentRepository.findBystudentEmailOrStudentRegistration(registerStudent.email(), registerStudent.email());
    if(studentAlreadyExist != null){
      return ResponseEntity.badRequest().body("User already exist");
    }else{
      student newStudent = new student(
              registerStudent.email(),this.passwordEncoder.encode(registerStudent.password()),
              registerStudent.Fullname(), registerStudent.cpf(), registerStudent.age(), UserRoles.STUDENT, registerStudent.classes()
      );
      newStudent.setRegistration(this.genRegistrationCodeService.genCode(newStudent.getStudentFullname()));
      this.studentRepository.save(newStudent);
      return ResponseEntity.ok("User created sucessfuly");
    }
  }

  @PostMapping("/registerTeacher")
  public ResponseEntity registerNewTeacher(@RequestBody registerTeacherDTO registerTeacher){
    UserDetails teacherAlreadyExist = teacherRepository.findByteacherEmail(registerTeacher.teacherEmail());
    if(teacherAlreadyExist != null){
      return ResponseEntity.badRequest().body("User already exist");
    }else{
      String teacherRegistration = this.genRegistrationCodeService.genCode(registerTeacher.teacherName());
      teacher newTeacher = new teacher(
              registerTeacher.teacherEmail(),this.passwordEncoder.encode(registerTeacher.teacherPassword()), registerTeacher.teacherName(),
              registerTeacher.teacherSubject(), registerTeacher.teacherCPF(), teacherRegistration, UserRoles.TEACHER
      );

      List<classes> allClasses = new ArrayList<>();
      registerTeacher.turmas().forEach(turma -> {
        String uuidString = turma;
        if(uuidString.length() == 32) {
          uuidString = uuidString.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})", "$1-$2-$3-$4-$5");
        }
        classes aClass = this.classesRepository.findByclassID(UUID.fromString(uuidString));
        allClasses.add(aClass);
      });

      this.teacherRepository.save(newTeacher);

      teacher getTeacherAgain = this.teacherRepository.findByteacherRegistration(teacherRegistration);
      if(getTeacherAgain != null){
        allClasses.forEach(classes -> {
          TeacherClasses newRegistration = new TeacherClasses(getTeacherAgain,classes);
          this.teacherClassesRepository.save(newRegistration);
        });
      }else{
        return ResponseEntity.internalServerError().body("Error on saving teacher");
      }

      return ResponseEntity.ok("User created sucessfuly");
    }
  }

  @PostMapping("/registerClass")
  public ResponseEntity registerNewClass(@RequestBody classes classesToRegister){
   if(this.classesRepository.findByclassName(classesToRegister.getClassName()) != null){
     return ResponseEntity.badRequest().body("class already exist");
   }else{
     classes newClass = new classes(classesToRegister.getClassName(), classesToRegister.getGradeType(), classesToRegister.getGradeNumber());
     this.classesRepository.save(newClass);
     this.configService.setClassesChanged(true);
     return ResponseEntity.ok("class created sucessfuly");
   }
  }
}
