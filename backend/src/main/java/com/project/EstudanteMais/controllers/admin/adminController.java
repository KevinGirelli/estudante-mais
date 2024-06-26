package com.project.EstudanteMais.controllers.admin;

import com.project.EstudanteMais.Entity.UserRoles;
import com.project.EstudanteMais.Entity.dto.registerStudentDTO;
import com.project.EstudanteMais.Entity.dto.registerTeacherDTO;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  PasswordEncoder passwordEncoder;

  @PostMapping("/registerStudent")
  public ResponseEntity registerNewStudent(@RequestBody registerStudentDTO registerStudent){
    UserDetails studentAlreadyExist = studentRepository.findBystudentEmail(registerStudent.email());
    if(studentAlreadyExist != null){
      return ResponseEntity.badRequest().body("User already exist");
    }else{
      student newStudent = new student(
              registerStudent.email(),this.passwordEncoder.encode(registerStudent.password()), registerStudent.firstName(),
              registerStudent.lastName(), registerStudent.cpf(), registerStudent.age(), UserRoles.STUDENT
      );
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
      teacher newTeacher = new teacher(
              registerTeacher.teacherEmail(),this.passwordEncoder.encode(registerTeacher.teacherPassword()), registerTeacher.teacherName(),
              registerTeacher.teacherSubject(), UserRoles.TEACHER
      );
      this.teacherRepository.save(newTeacher);

      return ResponseEntity.ok("User created sucessfuly");
    }
  }
}
