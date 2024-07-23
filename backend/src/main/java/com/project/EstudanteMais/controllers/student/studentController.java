package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.repository.assessmentRepository;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.studentRepository;
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
@RequestMapping("/student")
public class studentController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  assessmentRepository assessmentRepository;
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
}
