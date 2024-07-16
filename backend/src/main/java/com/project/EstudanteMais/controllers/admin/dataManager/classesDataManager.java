package com.project.EstudanteMais.controllers.admin.dataManager;


import com.project.EstudanteMais.Entity.dto.classesDTO;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/classesDataManager")
public class classesDataManager {

  @Autowired
  configPreferencesService configService;

  @Autowired
  UUIDformatter uuiDformatter;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  studentRepository studentRepository;

  @GetMapping("/getClassesAsync")
  public ResponseEntity getClasses(){
      var allClasses = this.classesRepository.findAll();
      List<classesDTO> allClassesDTO = new ArrayList<>();

      allClasses.forEach(classes -> {
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName(),
          classes.getGradeNumber(),classes.getGradeType(),classes.getClassMonitor().getTeacherName());
          allClassesDTO.add(classesDTO);
      });
      return ResponseEntity.ok(allClassesDTO);
    }

  @GetMapping("/getAllStudentsFromClass/{classID}")
  public ResponseEntity getAllStudentsFromClass(@PathVariable(value = "classID") String id){
     var allStudents = this.studentRepository.getAllStudentFromClass(UUID.fromString(id));
     if(allStudents != null){
       return ResponseEntity.ok(allStudents);
     }else{
       return ResponseEntity.internalServerError().build();
     }
  }
}
