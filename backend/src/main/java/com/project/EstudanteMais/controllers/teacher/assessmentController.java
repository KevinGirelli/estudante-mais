package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.createAssessmentDTO;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.repository.assessmentRepository;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.subjectsRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assess")
public class assessmentController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @PostMapping("/createNewAssessment")
  public ResponseEntity createNewAssessment(@RequestBody createAssessmentDTO asses){
    assessment newAssessment = new assessment(
            asses.name(),
            asses.data(),
            this.classesRepository.findByclassID(UUID.fromString(asses.classID())),
            this.teacherRepository.findByteacherID(UUID.fromString(asses.teacherID())),
            this.subjectsRepository.findBysubjectID(UUID.fromString(asses.subjectID()))
    );

    this.assessmentRepository.save(newAssessment);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/getAssessmentFromTeacher/{teacherID}")
  public ResponseEntity getAssessmentFromTeacher(@PathVariable(value = "teacherID")String teacherID){
    List<assessment> allAsses = this.assessmentRepository.findByteacher(this.teacherRepository.findByteacherID(UUID.fromString(teacherID)));
    if(allAsses != null){
      List<returnAssessmentDTO> assesDTO = new ArrayList<>();
      allAsses.forEach(asses ->{
        returnAssessmentDTO add = new returnAssessmentDTO(
                asses.getAssessmentID().toString(),
                asses.getAssessmentName(),
                asses.getClasses().getClassName(),
                asses.getSubjects().getSubjectname(),
                asses.getAssessmentDate(),
                asses.getClasses().getClassID().toString(),
                asses.getSubjects().getSubjectID().toString()
        );
        assesDTO.add(add);
      });
      return ResponseEntity.ok(assesDTO);
    }

    return ResponseEntity.internalServerError().build();
  }
}
