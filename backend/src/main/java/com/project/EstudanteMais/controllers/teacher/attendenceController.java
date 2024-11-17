package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.Entity.dto.registryAttendenceDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.formatDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/attendence")
public class attendenceController {

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @Autowired
  attendenceHistoryRepository attendenceHistoryRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  formatDateService formatDateService;

  @Autowired
  schoolSettingsRepository schoolSettingsRepository;

  @PostMapping("/registryAttendence")
  public ResponseEntity registryNewAttendence(@RequestBody registryAttendenceDTO data){
    classes getClass = this.classesRepository.findByclassID(UUID.fromString(data.classID()));
    teacher getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(data.teacherID()));
    subjects getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(data.subjectID()));
    String formattedDate  = this.formatDateService.formatDate(data.date());
    var config = this.schoolSettingsRepository.getConfig();

    var periodType = getSubject.getSubjectPeriod();
    AtomicInteger periodNumber = new AtomicInteger(0);
    if(periodType == 2) periodNumber.set(config.getBimestralNumber());
    if(periodType == 3) periodNumber.set(config.getTrimestralNumber());
    if(periodType == 6) periodNumber.set(config.getSemestralNumber());

    AtomicBoolean alreadyPosted = new AtomicBoolean();

    var verify = this.attendenceHistoryRepository.findByregistrationDate(formattedDate);
    verify.forEach(a ->{
      if(a.getMissedClasses().getClasses() == getClass){
        if(a.getMissedClasses().getTeacher() == getTeacher){
          if(a.getMissedClasses().getSubjects() == getSubject){
            alreadyPosted.set(true);
          }
        }
      }
    });

    if(alreadyPosted.get()){
      return ResponseEntity.badRequest().build();
    }

    data.students().forEach(students ->{
      var split = students.split(",");
      student getStudent = this.studentRepository.findBystudentID(UUID.fromString(split[0]));
      attendence newAtt = new attendence(getStudent,getClass,getTeacher,
              attendenceStatus.valueOf(split[1]),periodNumber.get(),getSubject,data.quantity(),formattedDate);

      attendence savedAttendece = this.attendenceRepository.save(newAtt);

      attendenceHistory registry = new attendenceHistory(
              formattedDate,
              savedAttendece
      );
      this.attendenceHistoryRepository.save(registry);
    });
    return ResponseEntity.ok().build();
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
}
