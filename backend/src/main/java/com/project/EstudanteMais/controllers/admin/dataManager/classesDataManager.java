package com.project.EstudanteMais.controllers.admin.dataManager;


import com.project.EstudanteMais.Entity.classes_subjects;
import com.project.EstudanteMais.Entity.dto.avaliableClassesDTO;
import com.project.EstudanteMais.Entity.dto.classesDTO;
import com.project.EstudanteMais.Entity.dto.subjectsDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @Autowired
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  teacherRepository teacherRepository;

  @GetMapping("/getClassesAsync")
  public ResponseEntity getClasses(){
      var allClasses = this.classesRepository.findAll();
      List<classesDTO> allClassesDTO = new ArrayList<>();

      allClasses.forEach(classes -> {
        if(classes.getClassMonitor() != null){
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName(),
                  classes.getGradeNumber(),classes.getGradeType(),classes.getClassMonitor().getTeacherName());
          allClassesDTO.add(classesDTO);
        }else{
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName(),
                  classes.getGradeNumber(),classes.getGradeType(),null);
          allClassesDTO.add(classesDTO);
        }

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

  @GetMapping("/getSearchAllClassesRelatedToSubject/{subjectsIDS}/{teacherID}")
  public ResponseEntity getAllClassesRelatedToSubject(@PathVariable(value = "subjectsIDS") String subjects, @PathVariable(value = "teacherID")String teacherID){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));

    List<String> ids = new ArrayList<>();
    var split = subjects.split(",");
    for(int c = 0; c <= split.length-1; c++){
      ids.add(split[c]);
    }
    List<classes_subjects> allClasses = new ArrayList<>();
    List<avaliableClassesDTO> avaliableClasses = new ArrayList<>();
    List<classes_subjects> ownTeacherClasses = new ArrayList<>();

    ids.forEach(subject ->{
        var getSubject =  this.subjectsRepository.findBysubjectID(UUID.fromString(subject));
        List<classes_subjects> allClassesBySubject = this.classesSubjectsRepository.findBysubjects(getSubject);
        allClassesBySubject.forEach(classToADD -> {
            var verify = this.teacherClassesRepository.findByClassesAndSubjects(classToADD.getClasses(),classToADD.getSubjects());
            if(verify == null){
              allClasses.add(classToADD);
            }else{
              if(verify.getTeacher().getTeacherID().toString().equals(getTeacher.getTeacherID().toString())){
                ownTeacherClasses.add(classToADD);
              }
            }
        });
    });
    allClasses.forEach(classes ->{
      avaliableClassesDTO addClass = new avaliableClassesDTO(
              this.uuiDformatter.formatUuid(classes.getClasses().getClassID()),
              this.uuiDformatter.formatUuid(classes.getSubjects().getSubjectID()),
              classes.getClasses().getClassName(),
              classes.getSubjects().getSubjectname(),
              classes.getNumberOfClasses(),
              false
      );
      avaliableClasses.add(addClass);
    });

    ownTeacherClasses.forEach(classes ->{
      if(!avaliableClasses.contains(classes)){
        avaliableClassesDTO addClass = new avaliableClassesDTO(
                this.uuiDformatter.formatUuid(classes.getClasses().getClassID()),
                this.uuiDformatter.formatUuid(classes.getSubjects().getSubjectID()),
                classes.getClasses().getClassName(),
                classes.getSubjects().getSubjectname(),
                classes.getNumberOfClasses(),
                true
        );
        avaliableClasses.add(addClass);
      }
    });
    return ResponseEntity.ok(avaliableClasses);
  }
}
