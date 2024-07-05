package com.project.EstudanteMais.controllers.admin.dataManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.EstudanteMais.repository.studentRepository;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/studentDataManager")
public class studentDataManager {

  @Autowired
  studentRepository studentRepository;

  //Students crud
  @GetMapping("/getStudent/{studentID}")
  public ResponseEntity selectStudent(@PathVariable(value = "studentID") String ID){
    UserDetails gotStudent = this.studentRepository.findBystudentEmailOrStudentRegistration(ID,ID);

    if(gotStudent != null){
      return ResponseEntity.status(HttpStatus.FOUND).body(gotStudent);
    }else{
      return ResponseEntity.badRequest().body("user not found");
    }
  }
}

