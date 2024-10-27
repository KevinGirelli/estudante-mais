package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.postStudentGradeDTO;
import com.project.EstudanteMais.Entity.grade;
import com.project.EstudanteMais.repository.assessmentRepository;
import com.project.EstudanteMais.repository.gradeRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.subjectsRepository;
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
@RequestMapping("/grades")
public class studentGrades {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  gradeRepository gradeRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  configPreferencesService configPreferencesService;

  @GetMapping("/viewGrades/{ids}")
  public ResponseEntity viewGrades(@PathVariable(value = "ids")String ids){
    var split = ids.split(",");
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(split[0]));
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(split[1]));
    List<postStudentGradeDTO> formatGrades = new ArrayList<>();
    if(getStudent != null){
      List<assessment> studentAssess = this.assessmentRepository.findByclassesAndSubjects(getStudent.getClasses(),getSubject);
      studentAssess.forEach(s -> {
        List<grade> studentGrades = this.gradeRepository.findByStudentAndQuarterAndAssessment(
                getStudent,
                Integer.parseInt(split[2]),
                s
        );
        studentGrades.forEach(g ->{
          postStudentGradeDTO add = new postStudentGradeDTO(
                  g.getGradeValue(),
                  g.getStudent().getStudentID().toString(),
                  g.getAssessment().getAssessmentID().toString(),
                  g.getAssessment().getAssessmentName(),
                  g.getQuarter()
          );
          formatGrades.add(add);
        });
      });
      return ResponseEntity.ok(formatGrades);
    }
    return ResponseEntity.internalServerError().build();
  }

}
