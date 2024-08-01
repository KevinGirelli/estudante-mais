package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.dto.createAssessmentDTO;
import com.project.EstudanteMais.Entity.dto.postStudentGradeDTO;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.Entity.grade;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.notificationService.notificationDTO;
import com.project.EstudanteMais.services.notificationService.notificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assess")
public class assessmentController {

  @Autowired
  UUIDformatter uuiDformatter;
  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  notificationManager notificationManager;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  gradeRepository gradeRepository;

  @PostMapping("/createNewAssessment")
  public ResponseEntity createNewAssessment(@RequestBody createAssessmentDTO asses){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(asses.classID()));
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(asses.teacherID()));
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(asses.subjectID()));

    var splitDate = asses.data().split("T");
    var splitData2 = splitDate[0];
    var splitData3 = splitData2.split("-");
    var formattedDate = splitData3[2] + "/" + splitData3[1] + "/" +splitData3[0];
    assessment newAssessment = new assessment(
            asses.name(),
            formattedDate,
            getClass,
            getTeacher,
            getSubject
    );
    String notificationName = "Nova avaliação postada por seu professor " + getTeacher.getTeacherName();
    notificationDTO newNotification = new notificationDTO("",notificationName,"");
    this.notificationManager.createNotificationForStudents(this.classesRepository.findByclassID(UUID.fromString(asses.classID())),newNotification);
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
                asses.getSubjects().getSubjectID().toString(),
                asses.getTeacher().getTeacherName(),
                asses.getTeacher().getTeacherID().toString()
        );
        assesDTO.add(add);
      });
      return ResponseEntity.ok(assesDTO);
    }

    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/updateAssessment")
  public ResponseEntity updateAssessment(@RequestBody returnAssessmentDTO asses){
      this.assessmentRepository.updateAssessment(
              asses.name(),
              asses.date(),
              UUID.fromString(asses.classID()),
              UUID.fromString(asses.subjectID()),
              UUID.fromString(asses.id())
      );
      return ResponseEntity.ok().build();
  }

  @PostMapping("/postStudentGrade")
  public ResponseEntity postStudentGrade(@RequestBody postStudentGradeDTO newGrade){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(newGrade.studentID()));
    var getAsses = this.assessmentRepository.findByassessmentID(UUID.fromString(newGrade.assessID()));

    if(getStudent != null && getAsses != null){
      var checkGrade = this.gradeRepository.findByAssessmentAndStudent(getAsses,getStudent);
      if(checkGrade != null){
          this.gradeRepository.updateStudentGradeValue(newGrade.gradeValue(),getStudent.getStudentID());
          String notificationName = "Sua nota em " + getAsses.getAssessmentName() + ", foi alterada";
          notificationDTO newNotification = new notificationDTO(UUID.randomUUID().toString(),notificationName,
                  this.uuiDformatter.formatUuid(getStudent.getStudentID()));
          this.notificationManager.CreateNotification(newNotification);
          return ResponseEntity.ok().build();
      }

      grade grade = new grade(
              newGrade.gradeValue(),
              getAsses,
              getStudent,
              getAsses.getAssessmentDate(),
              this.configPreferencesService.getCurrentQuarterType()
      );
      this.gradeRepository.save(grade);

      String notificationName = "Sua nota em " + getAsses.getAssessmentName() + ", foi postada";
      notificationDTO newNotification = new notificationDTO(UUID.randomUUID().toString(),notificationName,
              this.uuiDformatter.formatUuid(getStudent.getStudentID()));
      this.notificationManager.CreateNotification(newNotification);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }
}
