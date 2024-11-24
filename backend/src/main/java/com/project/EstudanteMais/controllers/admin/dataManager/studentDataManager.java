package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.Entity.attendenceStatus;
import com.project.EstudanteMais.Entity.dto.boletim.boletimDTO;
import com.project.EstudanteMais.Entity.dto.boletim.subjectBoletim;
import com.project.EstudanteMais.Entity.dto.updateStudentDataDTO;
import com.project.EstudanteMais.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/admin/studentDataManager")
public class studentDataManager {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @Autowired
  attendenceHistoryRepository attendenceHistoryRepository;

  @Autowired
  gradeRepository gradeRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  classesRepository classesRepository;


  //Students crud
  @GetMapping("/getStudent/{studentID}")
  public ResponseEntity selectStudent(@PathVariable(value = "studentID") String ID){
    UserDetails gotStudent = this.studentRepository.findBystudentEmailOrStudentRegistration(ID,ID);

    if(gotStudent != null && gotStudent.isEnabled()){
      return ResponseEntity.status(HttpStatus.FOUND).body(gotStudent);
    }else{
      return ResponseEntity.badRequest().body("user not found");
    }
  }



  @PatchMapping("/updateStudentPrimaryData")
  public ResponseEntity updateStudentPrimaryData(@RequestBody updateStudentDataDTO studentData){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentData.studentID()));
    if(getStudent != null && getStudent.isStudentEnable()){
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

  @DeleteMapping("/deleteStudent/{studentID}")
  public ResponseEntity deleteStudent(@PathVariable(value = "studentID")String studentID){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentID));

    if(getStudent != null){
      this.studentRepository.disableStudent(getStudent.getStudentID());
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.badRequest().build();
  }
}

