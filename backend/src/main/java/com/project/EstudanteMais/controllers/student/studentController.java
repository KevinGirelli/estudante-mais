package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String add = s.getSubjects().getSubjectID().toString() + "," + s.getSubjects().getSubjectname();
        subjects.add(add);
      });

      return ResponseEntity.ok(subjects);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/viewSchedule")
  public ResponseEntity viewStudentClassSchedule(){
    this.callScheduleRequestService.callRequest();
    return ResponseEntity.ok().build();
  }
}
