package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.dto.updateTeacherDataDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  configPreferencesService configPreferencesService;

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

      teacherSubjects.forEach(subject->{
        listSubjects.add(subject.getSubject().getSubjectname());
      });

      teachersDTO addTeacher = new teachersDTO(this.uuiDformatter.formatUuid(teacher.getTeacherID()),teacher.getTeacherName()
      ,teacher.getTeacherEmail(),teacher.getTeacherCPF(),listSubjects);
      allTeachers.add(addTeacher);
    });
    return ResponseEntity.ok(allTeachers);
  }

  @GetMapping("/getAllTeacherFromClass/{classID}")
  public ResponseEntity getAllTeacherFromClass(@PathVariable(value = "classID") String classID){
    List<TeacherClasses> teacherClasses = this.teacherClassesRepository.findByclasses(this.classesRepository.findByclassID(UUID.fromString(classID)));
    List<teachersDTO> teachersDTOS = new ArrayList<>();
    System.out.println(this.classesRepository.findByclassID(UUID.fromString(classID)));
    teacherClasses.forEach(teacher ->{
      List<teacherSubject> subjects = this.teacherSubjectRepository.findByteacher(teacher.getTeacher());
      List<String> teacherSubjects = new ArrayList<>();

      subjects.forEach(subject ->{
        teacherSubjects.add(subject.getSubject().getSubjectname());
      });

      teachersDTO add = new teachersDTO(
              teacher.getTeacher().getTeacherID().toString(),
              teacher.getTeacher().getTeacherName(),
              teacher.getTeacher().getTeacherEmail(),
              teacher.getTeacher().getTeacherCPF(),
              teacherSubjects
      );
      teachersDTOS.add(add);
    });
    return ResponseEntity.ok(teachersDTOS);
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

        if(this.teacherClassesRepository.findByClassesAndSubjects(getClass,getSubject) == null){
          TeacherClasses newRegistry = new TeacherClasses(getTeacher,getClass,getSubject);
          this.teacherClassesRepository.save(newRegistry);
        }
      });

      teacherData.removeTeacherClasses().forEach(removeTeacher ->{
        var split = removeTeacher.split(",");
        var getClass = this.classesRepository.findByclassID(UUID.fromString(split[1]));
        var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(split[0]));

        var verify = this.teacherClassesRepository.findByClassesAndSubjectsAndTeacher(getClass,getSubject,getTeacher);
          if(verify != null){
            this.teacherClassesRepository.deleteTeacherFromClass(verify.getClasses().getClassID(),verify.getTeacher().getTeacherID(),verify.getSubjects().getSubjectID());
          }
      });

      this.teacherRepository.updateTeacherPrimaryData(teacherData.nome(),teacherData.email(),teacherData.cpf(),UUID.fromString(teacherData.teacherID()));
    }
    return ResponseEntity.ok().build();
  }

  @GetMapping("/getTeachersFromSubject/{subjectID}/{classID}")
  public ResponseEntity getTeachersFromSubject(@PathVariable(value = "subjectID")String subjectID, @PathVariable(value = "classID")String classID){
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subjectID));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));

    List<teachersDTO> returnTeachers = new ArrayList<>();

    if(getSubject != null && getClass != null){
      var TeachersFromSubject = this.teacherSubjectRepository.findBySubject(getSubject);
      var classSubject = this.classesSubjectsRepository.findBySubjectsAndClasses(getSubject,getClass).get(0);

      TeachersFromSubject.forEach(ts ->{
        if(ts.getSubject().getSubjectID().toString().equals(getSubject.getSubjectID().toString())){
          var teacherClasses = this.teacherClassesRepository.findByteacher(ts.getTeacher());
          AtomicInteger teacherAmountOfClasses = new AtomicInteger(classSubject.getNumberOfClasses());

          teacherClasses.forEach(tc -> {
            var getClassSubject = this.classesSubjectsRepository.findBySubjectsAndClasses(tc.getSubjects(),tc.getClasses()).get(0);
            teacherAmountOfClasses.addAndGet(getClassSubject.getNumberOfClasses());
          });

          if(teacherAmountOfClasses.get() < this.configPreferencesService.getMaxClassPeerWeek()){
            teachersDTO add = new teachersDTO(teacherClasses.get(0).getTeacher().getTeacherID().toString(),
                    teacherClasses.get(0).getTeacher().getTeacherName(),"","",null);

            returnTeachers.add(add);
          }
        }
      });

      return ResponseEntity.ok(returnTeachers);
    }

    return ResponseEntity.internalServerError().build();
  }
}
