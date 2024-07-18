package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.repository.teacherSubjectRepository;
import com.project.EstudanteMais.services.UUIDformatter;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/teacherDataManager")
public class teacherDataManager {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;

  @Autowired
  UUIDformatter uuiDformatter;
  @GetMapping("/getTeacher/{teacherEmail}")
  public ResponseEntity selectTeacher(@PathVariable(value = "teacherEmail")String email){
    UserDetails gotTeacher = this.teacherRepository.findByteacherEmail(email);

    if(gotTeacher != null){
      return ResponseEntity.status(HttpStatus.FOUND).body(gotTeacher);
    }else{
      return ResponseEntity.badRequest().body("user not found");
    }
  }

  @GetMapping("/getAllTeachers")
  public ResponseEntity getAllTeachers(){
    var teachers = this.teacherRepository.findAll();
    List<teachersDTO> allTeachers = new ArrayList<>();

    teachers.forEach(teacher ->{
      List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(teacher);
      List<String> listSubjects = new ArrayList<>();

      teacherSubjects.forEach(subject ->{
        listSubjects.add(subject.getSubject().getSubjectname());
      });

      teachersDTO addTeacher = new teachersDTO(this.uuiDformatter.formatUuid(teacher.getTeacherID()),teacher.getTeacherName()
      ,teacher.getTeacherEmail(),teacher.getTeacherCPF(),listSubjects);
      allTeachers.add(addTeacher);
    });
    return ResponseEntity.ok(allTeachers);
  }
}
