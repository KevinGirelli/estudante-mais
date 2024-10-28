package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.repository.subjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/subjectDataManager")
public class subjectsDataManager {

  @Autowired
  subjectsRepository subjectsRepository;

  @GetMapping("/getSubjects")
  public ResponseEntity getAllSubjects(){
    var allSubjects = this.subjectsRepository.findAll();
    if(allSubjects != null){
      return ResponseEntity.ok(allSubjects);
    }else{
      return ResponseEntity.internalServerError().build();
    }
  }


}
