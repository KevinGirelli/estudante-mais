package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.dto.classesDTO;
import com.project.EstudanteMais.Entity.dto.postStudentGradeDTO;
import com.project.EstudanteMais.Entity.dto.subjectsDTO;
import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.excelToJsonModel;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
public class teacherController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  gradeRepository gradeRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  callScheduleRequestService callScheduleRequestService;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;
  @GetMapping("/getTeacherSubject/{teacherID}")
  public ResponseEntity getTeacherSubject(@PathVariable(value = "teacherID")String teacherID){
    List<TeacherClasses> teacherSubjects = this.teacherClassesRepository.findByteacher(this.teacherRepository.findByteacherID(UUID.fromString(teacherID)));
    subjectsDTO subjects = new subjectsDTO();
    if(teacherSubjects != null){
        List<String> temp = new ArrayList<>();
        teacherSubjects.forEach(subject ->{
          temp.add(subject.getSubjects().getSubjectID().toString() + "," + subject.getSubjects().getSubjectname() + ","
          + subject.getClasses().getClassName() + "," + subject.getClasses().getClassID().toString());
        });
        subjects.setSubjectsIDS(temp);
        return ResponseEntity.ok(subjects);
    }

    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getAllClassesFromTeacher/{teacherID}")
  public ResponseEntity getClassesFromTeacher(@PathVariable(value = "teacherID")String teacherID){
    List<TeacherClasses> teacherClasses = this.teacherClassesRepository.findByteacher(this.teacherRepository.findByteacherID(UUID.fromString(teacherID)));
    if(teacherClasses != null){
      List<classesDTO> classFromTeacher = new ArrayList<>();
      teacherClasses.forEach(classes ->{
        classesDTO add = new classesDTO(
                classes.getClasses().getClassID().toString(),
                classes.getClasses().getClassName(),
                classes.getClasses().getGradeNumber(),
                classes.getClasses().getGradeType(),
                classes.getTeacher().getTeacherName()
        );
        classFromTeacher.add(add);
      });

      return ResponseEntity.ok(classFromTeacher);
    }

    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getAllStudentsFromClass/{ids}")
  public ResponseEntity getAllStudentsFromClass(@PathVariable(value = "ids") String id){
    var split = id.split(",");
    var allStudents = this.studentRepository.getAllStudentFromClass(UUID.fromString(split[1]));
    var getAssess = this.assessmentRepository.findByassessmentID(UUID.fromString(split[0]));
    List<postStudentGradeDTO> students = new ArrayList<>();

    if(allStudents != null){
      allStudents.forEach(s ->{
        var getGrade = this.gradeRepository.findByAssessmentAndStudent(getAssess,this.studentRepository.findBystudentID(s.getStudentID()));
        if(getGrade != null){
          postStudentGradeDTO add = new postStudentGradeDTO(
                  getGrade.getGradeValue(),
                  s.getStudentID().toString(),
                  getAssess.getAssessmentID().toString(),
                  s.getStudent_fullname(),
                  0
          );
          students.add(add);
        }else{
          postStudentGradeDTO add = new postStudentGradeDTO(
                  0,
                  s.getStudentID().toString(),
                  getAssess.getAssessmentID().toString(),
                  s.getStudent_fullname(),
                  0
          );
          students.add(add);
        }
      });
      return ResponseEntity.ok(students);
    }else{
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/getTeacherSchedule/{ids}")
  public ResponseEntity getTeacherSchedule(@PathVariable(value = "ids")String ids) throws IOException {
    if(this.configPreferencesService.isScheduleGenerated()) {
      var getClass = this.classesRepository.findByclassID(UUID.fromString(ids));
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
      return ResponseEntity.badRequest().build();
    }
  }
}
