package com.project.EstudanteMais.controllers.admin.dataManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.EstudanteMais.Entity.dto.studentDataDTO;
import com.project.EstudanteMais.Entity.dto.updateStudentDataDTO;
import com.project.EstudanteMais.repository.studentRepository;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

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

  @PatchMapping("/updateStudentPrimaryData")
  public ResponseEntity updateStudentPrimaryData(@RequestBody updateStudentDataDTO studentData){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentData.studentID()));
    if(getStudent != null){
      if(!getStudent.getStudentEmail().equals(studentData.email())){
        if(this.studentRepository.findBystudentEmailOrStudentRegistration(studentData.email(),studentData.email()) != null){
          return ResponseEntity.badRequest().build();
        }
      }
      if(getStudent.getClasses().getClassID().toString().equals(studentData.classID())){
        this.studentRepository.updateStudentPrimaryData(studentData.fullname(),studentData.email(),studentData.cpf(),studentData.age(),UUID.fromString(studentData.studentID()));
        return ResponseEntity.ok().build();
      }else{
        this.studentRepository.updateStudentPrimaryData(studentData.fullname(),studentData.email(),studentData.cpf(),studentData.age(),UUID.fromString(studentData.studentID()));
        this.studentRepository.updateStudentClass(UUID.fromString(studentData.classID()),UUID.fromString(studentData.studentID()));
        return ResponseEntity.ok().build();
      }
    }else{
      return ResponseEntity.internalServerError().build();
    }
  }
}

