package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.dto.classesDTO;
import com.project.EstudanteMais.Entity.dto.subjectsDTO;
import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.*;
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
@RequestMapping("/teacher")
public class teacherController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;
  @GetMapping("/getTeacherSubject/{teacherID}")
  public ResponseEntity getTeacherSubject(@PathVariable(value = "teacherID")String teacherID){
    List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(this.teacherRepository.findByteacherID(UUID.fromString(teacherID)));
    subjectsDTO subjects = new subjectsDTO();
    if(teacherSubjects != null){
        List<String> temp = new ArrayList<>();
        teacherSubjects.forEach(subject ->{
          temp.add(subject.getSubject().getSubjectID().toString() + "," + subject.getSubject().getSubjectname());
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
