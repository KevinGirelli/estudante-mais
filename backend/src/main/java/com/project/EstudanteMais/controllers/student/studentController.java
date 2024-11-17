package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.excelToJsonModel;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class studentController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  callScheduleRequestService callScheduleRequestService;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  classes_subjectsRepository classesSubjectsRepository;
  @GetMapping("/getAssessments/{studentID}")
  public ResponseEntity getAssessments(@PathVariable(value = "studentID")String studentID){
      var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentID));
      if(getStudent != null){
        List<assessment> allAssess = this.assessmentRepository.findByclasses(getStudent.getClasses());
        List<returnAssessmentDTO> allAsessFilter = new ArrayList<>();
        allAssess.forEach(assess ->{
            returnAssessmentDTO add = new returnAssessmentDTO(
                    assess.getAssessmentID().toString(),
                    assess.getAssessmentName(),
                    assess.getClasses().getClassName(),
                    assess.getSubjects().getSubjectname(),
                    assess.getAssessmentDate(),
                    assess.getClasses().getClassID().toString(),
                    assess.getSubjects().getSubjectID().toString(),
                    assess.getTeacher().getTeacherName(),
                    assess.getTeacher().getTeacherID().toString()
            );
            allAsessFilter.add(add);
        });
        return ResponseEntity.ok(allAsessFilter);
      }else{
        return ResponseEntity.internalServerError().build();
      }
  }

  @GetMapping("/getSubjectsFromClasses/{studentID}")
  public ResponseEntity getSubjectFromClass(@PathVariable(value = "studentID")String id){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(id));
    List<String> subjects = new ArrayList<>();
    if(getStudent != null){
      var getClass = this.classesRepository.findByclassID(getStudent.getClasses().getClassID());
      var Classessubjects = this.classesSubjectsRepository.findByclasses(getClass);
      Classessubjects.forEach(s ->{
        String add = s.getSubjects().getSubjectID().toString() + "," + s.getSubjects().getSubjectname() + "," + s.getSubjects().getSubjectPeriod();
        subjects.add(add);
      });

      return ResponseEntity.ok(subjects);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/viewSchedule/{classID}")
  public ResponseEntity viewStudentClassSchedule(@PathVariable(value = "classID")String classID) throws IOException {
    if(this.configPreferencesService.isScheduleGenerated()){
      var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
      excelToJsonModel classFilter = new excelToJsonModel();

      var jsonModel = this.callScheduleRequestService.convertExcelToJson();
      jsonModel.getClasses().forEach(allClasses->{
        allClasses.getClassSchedule().forEach(c->{
          if(c.equals(getClass.getClassName())){
            classFilter.setHours(jsonModel.getHours());
            var temp = classFilter.getClasses();
            temp.add(allClasses);
            classFilter.setClasses(temp);
          }
        });
      });

      return ResponseEntity.ok(classFilter);
    }else{
      return ResponseEntity.notFound().build();
    }
  }
}
