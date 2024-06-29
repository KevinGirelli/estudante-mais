package com.project.EstudanteMais.controllers.admin.crud;

import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/dataManager")
public class dataManagerController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  studentRepository studentRepository;

  //Crud for entitys
  @GetMapping("/getStudent/{studentID}")
  public ResponseEntity selectStudent(@PathVariable(value = "studentID") String ID){
    UserDetails gotStudent = this.studentRepository.findBystudentEmailOrStudentRegistration(ID,ID);

    if(gotStudent != null){
      return ResponseEntity.status(HttpStatus.FOUND).body(gotStudent);
    }else{
      return ResponseEntity.badRequest().body("user not found");
    }
  }

  @GetMapping("/getTeacher/{teacherEmail}")
  public ResponseEntity selectTeacher(@PathVariable(value = "teacherEmail")String email){
    UserDetails gotTeacher = this.teacherRepository.findByteacherEmail(email);

    if(gotTeacher != null){
      return ResponseEntity.status(HttpStatus.FOUND).body(gotTeacher);
    }else{
      return ResponseEntity.badRequest().body("user not found");
    }
  }
}
