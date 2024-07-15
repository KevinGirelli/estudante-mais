package com.project.EstudanteMais.controllers.admin;

import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.Entity.dto.MessageDTO;
import com.project.EstudanteMais.Entity.dto.registerClassesDTO;
import com.project.EstudanteMais.Entity.dto.registerStudentDTO;
import com.project.EstudanteMais.Entity.dto.registerTeacherDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
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
public class registerController {

  @Autowired
  private adminRepository adminRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  configPreferencesService configService;

  @Autowired
  genRegistrationCodeService genRegistrationCodeService;

  private MessageDTO SucessMessage = new MessageDTO("User created sucessfuly");

  @PostMapping("/registerStudent")
  public ResponseEntity registerNewStudent(@RequestBody registerStudentDTO registerStudent){
    UserDetails studentAlreadyExist = studentRepository.findBystudentEmailOrStudentRegistration(registerStudent.email(), registerStudent.email());
    if(studentAlreadyExist != null){
      return ResponseEntity.badRequest().body("User already exist");
    }else{
      classes studentClass = this.classesRepository.findByclassID(UUID.fromString(registerStudent.classID()));
      if(studentClass != null){
        student newStudent = new student(
                registerStudent.email(),this.passwordEncoder.encode(registerStudent.password()),
                registerStudent.Fullname(), registerStudent.cpf(), registerStudent.age(),false, UserRoles.STUDENT, studentClass
        );
        newStudent.setRegistration(this.genRegistrationCodeService.genCode(newStudent.getStudentFullname()));
        this.studentRepository.save(newStudent);
        return ResponseEntity.ok(this.SucessMessage);
      }else{
        return ResponseEntity.badRequest().body("class not found.");
      }
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
              registerTeacher.teacherEmail(),this.passwordEncoder.encode(registerTeacher.teacherPassword()), registerTeacher.teacherName()
              , registerTeacher.teacherCPF(), teacherRegistration, false, UserRoles.TEACHER
      );

      this.teacherRepository.save(newTeacher);
      teacher getTeacher = this.teacherRepository.findByteacherRegistration(teacherRegistration);
      if(getTeacher != null){
        registerTeacher.subjects().forEach(subject ->{
            teacherSubject newRegistry = new teacherSubject(subject,getTeacher);
            this.teacherSubjectRepository.save(newRegistry);
        });
      }else{
        return ResponseEntity.internalServerError().build();
      }

      return ResponseEntity.ok(this.SucessMessage);
    }
  }

  @PostMapping("/registerClass")
  public ResponseEntity registerNewClass(@RequestBody registerClassesDTO classesToRegister){
   if(this.classesRepository.findByclassName(classesToRegister.className()) != null){
     return ResponseEntity.badRequest().body("class already exist");
   }else{
     teacher teacherMonitor = this.teacherRepository.findByteacherID(UUID.fromString("06f01105-4b4f-450c-abb7-497cc2dae671"));
     if(teacherMonitor != null){
       classes newClass = new classes(classesToRegister.className(), classesToRegister.gradeType(), classesToRegister.gradeNumber(),teacherMonitor);
       this.classesRepository.save(newClass);

       return ResponseEntity.ok(this.SucessMessage);
     }else{
       return ResponseEntity.badRequest().body("teacher not found.");
     }

   }
  }
}
