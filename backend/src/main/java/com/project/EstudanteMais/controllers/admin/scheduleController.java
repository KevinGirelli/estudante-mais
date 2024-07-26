package com.project.EstudanteMais.controllers.admin;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.classes_subjects;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.classes_subjectsRepository;
import com.project.EstudanteMais.repository.teacherClassesRepository;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.datamodelDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.classDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.settingsDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectGroupDTO;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/admin/schedule")
public class scheduleController {
  @Autowired
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  callScheduleRequestService callScheduleRequestService;
  @Autowired
  teacherClassesRepository teacherClassesRepository;
  @Autowired
  configPreferencesService configPreferencesService;
  @PostMapping("/genSchedule/{settings}")
  public ResponseEntity genSchedule(@PathVariable(value = "settings")String settings){
    if(!this.configPreferencesService.isScheduleGenerated()){
      datamodelDTO datamodel = new datamodelDTO();
      var setting = settings.split(",");
      settingsDTO set = new settingsDTO(
              Integer.parseInt(setting[0]),
              Integer.parseInt(setting[1]),
              Integer.parseInt(setting[2])
      );
      datamodel.setSettings(set);
      List<TeacherClasses> allTeachersSubjects = this.teacherClassesRepository.findAll();
      List<classes> allClasses = this.classesRepository.findAll();
      AtomicInteger groupIndex = new AtomicInteger();
      allClasses.forEach(c ->{
        classDTO addClass = new classDTO(
                c.getClassName(),
                Integer.toString(groupIndex.get()),
                false
        );
        var currentClasses = datamodel.getClasses();
        currentClasses.add(addClass);
        datamodel.setClasses(currentClasses);

        var AllClassesFromSubjects = this.teacherClassesRepository.findByclasses(c);
        List<subjectDTO> subjects = new ArrayList<>();
        AllClassesFromSubjects.forEach(c2 ->{
          var numberOfClasses = this.classesSubjectsRepository.findBySubjectsAndClasses(c2.getSubjects(),c2.getClasses());
          subjectDTO subject = new subjectDTO(
                  numberOfClasses.get(0).getSubjects().getSubjectname(),
                  c2.getTeacher().getTeacherName(),
                  numberOfClasses.get(0).getNumberOfClasses()
          );
          subjects.add(subject);
        });

        subjectGroupDTO subjectGroup = new subjectGroupDTO(
                Integer.toString(groupIndex.get()),
                subjects
        );
        var currentGroups = datamodel.getSubjectGroup();
        currentGroups.add(subjectGroup);
        datamodel.setSubjectGroup(currentGroups);
        groupIndex.set(groupIndex.get() + 1);
      });
      this.configPreferencesService.setScheduleModel(datamodel);
      this.callScheduleRequestService.callRequest();
      return ResponseEntity.ok().build();
    }
    this.configPreferencesService.setScheduleGenerated(true);
    return ResponseEntity.status(HttpStatus.FOUND).body(this.configPreferencesService.getScheduleModel());
  }

  @GetMapping("/getSchedule")
  public ResponseEntity getSchedule() throws IOException {
    if(this.configPreferencesService.isScheduleGenerated()){
      return ResponseEntity.ok(this.callScheduleRequestService.convertExcelToJson());
    }
    return ResponseEntity.badRequest().body("Schedule doesnt exist.");
  }
}
