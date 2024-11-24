package com.project.EstudanteMais.controllers.admin.dataManager;


import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.classes_subjects;
import com.project.EstudanteMais.Entity.dto.*;
import com.project.EstudanteMais.Entity.periodType;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/classesDataManager")
public class classesDataManager {

  @Autowired
  configPreferencesService configService;

  @Autowired
  UUIDformatter uuiDformatter;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  schoolSettingsRepository schoolSettingsRepository;

  @Autowired
  teacherRepository teacherRepository;

  @GetMapping("/getClassesAsync")
  public ResponseEntity getClassesAsync(){
      var allClasses = this.classesRepository.findAll();
      List<classesDTO> allClassesDTO = new ArrayList<>();

      allClasses.forEach(classes -> {
        if(classes.getClassMonitor() != null){
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName(),
                  classes.getGradeNumber(),classes.getGradeType(),classes.getClassMonitor().getTeacherName(),classes.getType());
          allClassesDTO.add(classesDTO);
        }else{
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName(),
                  classes.getGradeNumber(),classes.getGradeType(),null,classes.getType());
          allClassesDTO.add(classesDTO);
        }

      });
      return ResponseEntity.ok(allClassesDTO);
    }

  @GetMapping("/getClassSubjects/{classID}")
  public ResponseEntity getClassSubjects(@PathVariable(value = "classID")String classID){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));

    if(getClass != null){
      var getSubjects = this.classesSubjectsRepository.findByclasses(getClass);
      subjectsDTO subjects = new subjectsDTO();
      List<String> subjectsIDS = new ArrayList<>();

      getSubjects.forEach(s ->{
        var addIDS = s.getClassSubjectID().toString() + "," + s.getClasses().getClassID().toString() + "," + s.getSubjects().getSubjectID().toString() +
                "," + s.getClasses().getClassName() + "," + s.getSubjects().getSubjectname() + "," + s.getNumberOfClasses();
        subjectsIDS.add(addIDS);
      });

      subjects.setSubjectsIDS(subjectsIDS);
      return ResponseEntity.ok(subjects);
    }

    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getClass/{classID}")
  public ResponseEntity getClasses(@PathVariable(value = "classID")String classID){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
    if(getClass != null){
      classesDTO classDTO = new classesDTO(
              getClass.getClassID().toString(),
              getClass.getClassName(),
              getClass.getGradeNumber(),
              getClass.getGradeType(),
              "",
              getClass.getType()
      );

      return ResponseEntity.ok(classDTO);
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

  @GetMapping("/getClassSubjectTeacher/{classID}")
  public ResponseEntity getAllClassesRelatedToSubject(@PathVariable(value = "classID")String classID){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));

    if(getClass != null){
      List<TeacherClassesDTO> allTCs = new ArrayList<>();

      var ClassSubjects = this.classesSubjectsRepository.findByclasses(getClass);
      var TeacherClasses = this.teacherClassesRepository.findByclasses(getClass);

      ClassSubjects.forEach(cs ->{
        var tcLenght = allTCs.size();

        TeacherClasses.forEach(tc ->{
          if(tc.getSubjects().getSubjectID().toString().equals(cs.getSubjects().getSubjectID().toString())){
            TeacherClassesDTO add = new TeacherClassesDTO(
                    tc.getClasses().getClassID().toString(),
                    tc.getClasses().getClassName(),
                    tc.getTeacher().getTeacherID().toString(),
                    tc.getTeacher().getTeacherName(),
                    tc.getSubjects().getSubjectID().toString(),
                    tc.getSubjects().getSubjectname(),
                    cs.getNumberOfClasses()
            );
            allTCs.add(add);
          }
        });

        if(tcLenght >= allTCs.size()){
          TeacherClassesDTO add = new TeacherClassesDTO(
                  cs.getClasses().getClassID().toString(),
                  cs.getClasses().getClassName(),
                  "0",
                  "0",
                  cs.getSubjects().getSubjectID().toString(),
                  cs.getSubjects().getSubjectname(),
                  cs.getNumberOfClasses()
          );
          allTCs.add(add);
        }
      });

      return ResponseEntity.ok(allTCs);
    }

    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getSearchAllClassesRelatedToSubject/{subjectsIDS}/{teacherID}")
  public ResponseEntity getAllClassesRelatedToSubject(@PathVariable(value = "subjectsIDS") String subjects, @PathVariable(value = "teacherID")String teacherID){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));

    List<String> ids = new ArrayList<>();
    var split = subjects.split(",");
    for(int c = 0; c <= split.length-1; c++){
      ids.add(split[c]);
    }

    List<classes_subjects> allClasses = new ArrayList<>();
    List<avaliableClassesDTO> avaliableClasses = new ArrayList<>();
    List<classes_subjects> ownTeacherClasses = new ArrayList<>();

    ids.forEach(subject ->{
        var getSubject =  this.subjectsRepository.findBysubjectID(UUID.fromString(subject));
        List<classes_subjects> allClassesBySubject = this.classesSubjectsRepository.findBysubjects(getSubject);
        allClassesBySubject.forEach(classToADD -> {
            var verify = this.teacherClassesRepository.findByClassesAndSubjects(classToADD.getClasses(),classToADD.getSubjects());
            if(verify == null){
              allClasses.add(classToADD);
            }else{
              if(verify.getTeacher().getTeacherID().toString().equals(getTeacher.getTeacherID().toString())){
                ownTeacherClasses.add(classToADD);
              }
            }
        });
    });
    allClasses.forEach(classes ->{
      avaliableClassesDTO addClass = new avaliableClassesDTO(
              this.uuiDformatter.formatUuid(classes.getClasses().getClassID()),
              this.uuiDformatter.formatUuid(classes.getSubjects().getSubjectID()),
              classes.getClasses().getClassName(),
              classes.getSubjects().getSubjectname(),
              classes.getNumberOfClasses(),
              false
      );
      avaliableClasses.add(addClass);
    });

    ownTeacherClasses.forEach(classes ->{
      if(!avaliableClasses.contains(classes)){
        avaliableClassesDTO addClass = new avaliableClassesDTO(
                this.uuiDformatter.formatUuid(classes.getClasses().getClassID()),
                this.uuiDformatter.formatUuid(classes.getSubjects().getSubjectID()),
                classes.getClasses().getClassName(),
                classes.getSubjects().getSubjectname(),
                classes.getNumberOfClasses(),
                true
        );
        avaliableClasses.add(addClass);
      }
    });
    return ResponseEntity.ok(avaliableClasses);
  }

  @GetMapping("/getPeriodType")
    public ResponseEntity getPeriodType(){
      return ResponseEntity.ok(this.schoolSettingsRepository.getConfig().getPeriod().ordinal());
  }

  @PostMapping("/assignTeacherToClass/{teacherID}/{classID}/{subjectID}")
  public ResponseEntity assignTeacher(@PathVariable(value = "teacherID")String teacherID, @PathVariable(value = "classID")String classID,
                                      @PathVariable(value = "subjectID")String subjectID){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subjectID));

    if(getTeacher != null && getClass != null && getSubject != null){
      TeacherClasses newTeacher = new TeacherClasses(getTeacher,getClass,getSubject);
      this.teacherClassesRepository.save(newTeacher);
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/unAssignTeacher/{teacherID}/{classID}/{subjectID}")
  public ResponseEntity unAssignTeacher(@PathVariable(value = "teacherID")String teacherID, @PathVariable(value = "classID")String classID,
                                      @PathVariable(value = "subjectID")String subjectID){
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(subjectID));

    if(getTeacher != null && getClass != null && getSubject != null){
      var removeTeacher = this.teacherClassesRepository.findByClassesAndSubjectsAndTeacher(getClass,getSubject,getTeacher);

      if(removeTeacher != null){
        this.teacherClassesRepository.delete(removeTeacher);
        return ResponseEntity.ok().build();
      }

      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/setPeriodType/{type}")
    public ResponseEntity getPeriodType(@PathVariable(value = "type")String type){
    this.schoolSettingsRepository.updatePeriod(periodType.values()[Integer.parseInt(type)]);
      return ResponseEntity.ok().build();
    }

  @PatchMapping("/updateClassData/{classID}")
  public ResponseEntity updateClassData(@PathVariable(value = "classID")String classID, @RequestBody updateClassesDTO newClassData){
    this.classesRepository.updateClassPrimaryData(
            newClassData.classes().className(),
            newClassData.classes().gradeNumber(),
            newClassData.classes().gradeType(),
            newClassData.classes().type().ordinal(),
            UUID.fromString(classID)
    );

    newClassData.subjectDTO().getSubjectsIDS().forEach(s -> {
      var split = s.split(",");
      System.out.println(split[0]);
      System.out.println(split[1]);
      this.classesSubjectsRepository.updateQuantityOfClasses(Integer.parseInt(split[1]),UUID.fromString(split[0]));
    });

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/deleteClass/{classID}")
  public ResponseEntity deleteClass(@PathVariable(value = "classID")String classID){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));

    if(getClass != null){
      var studentFromClass = this.studentRepository.findByClass(getClass.getClassID());
      if(!studentFromClass.isEmpty()){
        return ResponseEntity.badRequest().build();
      }else{
        this.teacherClassesRepository.deleteClass(getClass.getClassID());
        this.classesSubjectsRepository.deleteClass(getClass.getClassID());
        this.classesRepository.delete(getClass);
        return ResponseEntity.ok().build();
      }
    }

    return ResponseEntity.internalServerError().build();
  }
}


