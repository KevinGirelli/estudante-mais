package com.project.EstudanteMais.controllers.admin.dataManager;

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
@RequestMapping("/admin/teacherDataManager")
public class teacherDataManager {

  @Autowired
  teacherRepository teacherRepository;
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
