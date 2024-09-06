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
import org.springframework.web.bind.annotation.*;

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
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  configPreferencesService configService;

  @Autowired
  genRegistrationCodeService genRegistrationCodeService;

  private MessageDTO SucessMessage = new MessageDTO("User created sucessfuly", "");

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
                registerStudent.Fullname(), registerStudent.cpf(), registerStudent.age(),false, UserRoles.STUDENT, studentClass,
                registerStudent.phoneNumber()
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
              , registerTeacher.teacherCPF(),registerTeacher.phoneNumber(), teacherRegistration, false, UserRoles.TEACHER
      );

      this.teacherRepository.save(newTeacher);
      teacher getTeacher = this.teacherRepository.findByteacherRegistration(teacherRegistration);
      if(getTeacher != null){
        registerTeacher.subjects().forEach(subject ->{
            subjects tempSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subject));
            teacherSubject newRegistry = new teacherSubject(tempSubject,getTeacher);
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
     classes newClass = new classes(classesToRegister.className(), classesToRegister.gradeType(), classesToRegister.gradeNumber(),null);
     this.classesRepository.save(newClass);

     classes getClass = this.classesRepository.findByclassName(classesToRegister.className());
     if(getClass != null){
       classesToRegister.subjects().forEach(subject ->{
         var split = subject.split("/");
         if(Integer.parseInt(split[1]) > 0){
           subjects newSubjects = this.subjectsRepository.findBysubjectID(UUID.fromString(split[0]));
           classes_subjects classesSubjects = new classes_subjects(newSubjects,Integer.parseInt(split[1]),getClass);
           this.classesSubjectsRepository.save(classesSubjects);
         }});


       return ResponseEntity.ok(this.SucessMessage);
     }else{
       return ResponseEntity.badRequest().body("teacher not found.");
     }

   }
  }

  @PostMapping("/registerSubject/{subjectName}")
  public ResponseEntity registerSubjects(@PathVariable(value = "subjectName") String subject){
    var subjectExist = this.subjectsRepository.findBysubjectname(subject);
    if(subjectExist != null){
      return ResponseEntity.badRequest().build();
    }

    subjects newSubject = new subjects(subject);
    this.subjectsRepository.save(newSubject);
    return ResponseEntity.ok("Subject created sucessfuly");
  }

  @PostMapping("/registerQuarterType/{type}")
  public ResponseEntity registerQuarterType(@PathVariable(value = "type")int type){
    this.configService.setQuarterType(type);
    return ResponseEntity.ok().build();
  }
}
