package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.createAssessmentDTO;
import com.project.EstudanteMais.repository.assessmentRepository;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/teacher")
public class assessmentController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @PostMapping("/createNewAssessment")
  public ResponseEntity createNewAssessment(@RequestBody createAssessmentDTO asses){
    System.out.println(asses);
    assessment newAssessment = new assessment(
            asses.name(),
            asses.data(),
            this.classesRepository.findByclassID(UUID.fromString(asses.classID())),
            this.teacherRepository.findByteacherID(UUID.fromString(asses.teacherID()))
    );

    this.assessmentRepository.save(newAssessment);
    return ResponseEntity.ok().build();
  }
}
