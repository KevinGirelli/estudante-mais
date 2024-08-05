package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.attendenceStatus;
import com.project.EstudanteMais.Entity.dto.registryAttendenceDTO;
import com.project.EstudanteMais.repository.attendenceRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.subjectsRepository;
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
@RequestMapping("/absense")
public class studentAbsense {

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @Autowired
  studentRepository studentRepository;


  @GetMapping("/getAbsenseFromSubject/{subjectAndQuarterAndStudent}")
  public ResponseEntity getAbsensesFromSubject(@PathVariable(value = "subjectAndQuarterAndStudent")String ids){
    var subjectID = ids.split(",")[0];
    var quarter = Integer.parseInt(ids.split(",")[1]);
    var studentID = ids.split(",")[2];

    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subjectID));
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentID));
    if(getSubject != null && getStudent != null){
      var absenses = this.attendenceRepository.findBysubjectsAndQuarterAndStudent(getSubject,quarter,getStudent);
      if(absenses != null){
        List<registryAttendenceDTO> filter = new ArrayList<>();
        List<String> blankField = new ArrayList<>();
        absenses.forEach(a ->{
          if(a.getStatus() == attendenceStatus.AUSENTE){
            registryAttendenceDTO addToFilter = new registryAttendenceDTO(
                    "",
                    "",
                    a.getNumberOfClasses(),
                    a.getMissDate(),
                    blankField,
                    ""
            );
            filter.add(addToFilter);
          }
        });

        if(filter != null){
          return ResponseEntity.ok(filter);
        }
      }
    }
    return ResponseEntity.internalServerError().build();
  }
}
