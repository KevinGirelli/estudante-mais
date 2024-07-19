package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.dto.updateTeacherDataDTO;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/teacherDataManager")
public class teacherDataManager {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

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

  @PatchMapping("/updateTeacherPrimaryData")
  public ResponseEntity updateTeacherPrimaryData(@RequestBody updateTeacherDataDTO teacherData){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherData.teacherID()));
    if(getTeacher != null){
      if(!getTeacher.getTeacherEmail().equals(teacherData.email())){
        if(this.teacherRepository.findByteacherEmail(teacherData.email()) != null){
          return ResponseEntity.badRequest().build();
        }
      }

      List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(getTeacher);
      if(teacherData.subjects().size() > teacherSubjects.size()){
        teacherData.subjects().forEach(subject ->{
          var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subject));
          if(this.teacherSubjectRepository.findByTeacherAndSubject(getTeacher,getSubject) == null){
            teacherSubject newRegistry = new teacherSubject(getSubject,getTeacher);
            this.teacherSubjectRepository.save(newRegistry);
          }
        });
      }

      teacherData.teacherClasses().forEach(teacherClass ->{
        var split = teacherClass.split(",");
        var getClass = this.classesRepository.findByclassID(UUID.fromString(split[1]));
        var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(split[0]));
        TeacherClasses newRegistry = new TeacherClasses(getTeacher,getClass,getSubject);
        this.teacherClassesRepository.save(newRegistry);
      });

      this.teacherRepository.updateTeacherPrimaryData(teacherData.nome(),teacherData.email(),teacherData.cpf(),UUID.fromString(teacherData.teacherID()));
    }
    return ResponseEntity.ok().build();
  }
}
