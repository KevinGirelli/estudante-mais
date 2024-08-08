package com.project.EstudanteMais.controllers.teacher;


import com.project.EstudanteMais.Entity.classDiary;
import com.project.EstudanteMais.Entity.dto.newDayDTO;
import com.project.EstudanteMais.repository.classDiaryRepository;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.subjectsRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.services.formatDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classDiary")
public class classDiaryController {
  @Autowired
  formatDateService formatDateService;
  @Autowired
  classDiaryRepository classDiaryRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @PostMapping("/registerNewDay")
  public ResponseEntity registerNewDay(@RequestBody newDayDTO data){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(data.teacherID()));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(data.classID()));
    var formattedDate = this.formatDateService.formatDate(data.registryDate());

    if(getTeacher != null && getClass != null){
      var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(data.subjectID()));
      if(this.classDiaryRepository.findByregistryDateAndClassesAndTeacherAndSubjects(formattedDate,getClass,getTeacher,getSubject) == null){
        classDiary newDay = new classDiary(
                null,
                getSubject,
                data.dayContent(),
                data.observations(),
                formattedDate,
                getClass,
                getTeacher
        );

        var getID = this.classDiaryRepository.save(newDay);
        return ResponseEntity.ok(getID.getDayID().toString());
      }else{
        return ResponseEntity.badRequest().build();
      }
    }else{
      return ResponseEntity.noContent().build();
    }
  }

  @GetMapping("/getClassDiary/{teacherID}/{classID}")
  public ResponseEntity getClassDiary(
          @PathVariable(value = "teacherID") String teacherID,
          @PathVariable(value = "classID") String classID
  ){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));

    if(getTeacher != null && getClass != null){
      List<classDiary> allClassesDiary = this.classDiaryRepository.findByTeacherAndClasses(getTeacher,getClass);
      List<newDayDTO> filterDays = new ArrayList<>();

      allClassesDiary.forEach(d ->{
        newDayDTO add = new newDayDTO(
                d.getDayID().toString(),
                d.getSubjects().getSubjectID().toString(),
                d.getSubjects().getSubjectname(),
                d.getDayContent(),
                d.getObservations(),
                d.getRegistryDate(),
                d.getClasses().getClassID().toString(),
                d.getClasses().getClassName(),
                d.getTeacher().getTeacherID().toString()
        );
        filterDays.add(add);
      });

      return ResponseEntity.ok(filterDays);
    }

    return ResponseEntity.internalServerError().build();
  }


  @PostMapping("/editClassDiary")
  public ResponseEntity editClassDiary(@RequestBody newDayDTO data){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(data.teacherID()));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(data.classID()));
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(data.subjectID()));
    var formatedDate = this.formatDateService.formatDate(data.registryDate());
    if(this.classDiaryRepository.findByregistryDateAndClassesAndTeacherAndSubjects(formatedDate,getClass,getTeacher,getSubject) == null){
      this.classDiaryRepository.updateClassDiary(
              data.dayContent(),
              data.observations(),
              formatedDate,
              UUID.fromString(data.subjectID()),
              UUID.fromString(data.diaryID())
      );
      return ResponseEntity.ok().build();
    }else{
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/deleteClassDiary/{diaryID}")
  public ResponseEntity deleteClassDiary(@PathVariable(value = "diaryID") String id){
    this.classDiaryRepository.deleteById(UUID.fromString(id));
    return ResponseEntity.ok().build();
  }
}
