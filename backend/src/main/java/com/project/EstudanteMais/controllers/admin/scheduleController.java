package com.project.EstudanteMais.controllers.admin;

import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.dto.MessageDTO;
import com.project.EstudanteMais.Entity.dto.schoolConfigDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.datamodelDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.classDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.settingsDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectGroupDTO;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/admin/schedule")
public class scheduleController {
  @Autowired
  classes_subjectsRepository classesSubjectsRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  classesRepository classesRepository;
  @Autowired
  callScheduleRequestService callScheduleRequestService;
  @Autowired
  teacherClassesRepository teacherClassesRepository;
  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  schoolSettingsRepository schoolSettingsRepository;

  @GetMapping("/getSchoolConfig")
  public ResponseEntity getSchoolConfig(){
      var config = this.schoolSettingsRepository.getConfig();
      schoolConfigDTO returnConfig = new schoolConfigDTO(
              config.getReportCardActivateBimestralDate(),
              config.getReportCardActivateTrimstralDate(),
              config.getReportCardActivateSemestralDate(),
              config.getPeriod().ordinal(),
              this.configPreferencesService.getMaxConsecutiveClass()
      );

      return ResponseEntity.ok(returnConfig);
  }

  @PostMapping("/genSchedule")
  public ResponseEntity genSchedule(){
    if(!this.configPreferencesService.isScheduleGenerated()){
      if(this.configPreferencesService.getMaxClassPeerWeek() == 0){
        return ResponseEntity.badRequest().build();
      }

      int getSchoolPeriod = this.schoolSettingsRepository.getConfig().getPeriod().ordinal();

      if(getSchoolPeriod == 2 || getSchoolPeriod ==  4 || getSchoolPeriod == 5){
        this.configPreferencesService.setMaxClassPeerWeek(50);
        this.configPreferencesService.setMaxClassPerDay(10);
      }else if(getSchoolPeriod == 6){
        this.configPreferencesService.setMaxClassPeerWeek(75);
        this.configPreferencesService.setMaxClassPerDay(15);
      }

      //Creating json datamodel to send to scheduleGen
      datamodelDTO datamodel = new datamodelDTO();
      settingsDTO set = new settingsDTO(
              this.configPreferencesService.getMaxConsecutiveClass(),
              this.configPreferencesService.getMaxClassPeerWeek(),
              this.configPreferencesService.getMaxClassPerDay(),
              this.schoolSettingsRepository.getConfig().getPeriod().ordinal()
      );
      datamodel.setSettings(set);
      List<classes> allClasses = this.classesRepository.findAll();
      AtomicInteger groupIndex = new AtomicInteger();
      allClasses.forEach(c ->{
        classDTO addClass = new classDTO(
                c.getClassName(),
                Integer.toString(groupIndex.get()),
                false,
                c.getType().ordinal()
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
                  numberOfClasses.get(0).getNumberOfClasses(),
                  c2.getTeacher().getTeacherWorkingDays()
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

      //checking if teacher has more classes than allowed
      var allTeachers = this.teacherRepository.findAll();
      AtomicReference<String> teacher = new AtomicReference<>("");
      AtomicInteger totalClassesNumber = new AtomicInteger();
      AtomicInteger teacherNumberOfClasses = new AtomicInteger();

      allTeachers.forEach(t-> {
        teacherNumberOfClasses.set(0);
        datamodel.getSubjectGroup().forEach(sj ->{
          sj.subjects().forEach(s->{
            if(s.teacherName().equals(t.getTeacherName())){
              teacherNumberOfClasses.set(teacherNumberOfClasses.get() + s.numberOfClasses());
            }
          });
        });

        if(teacherNumberOfClasses.get() > this.configPreferencesService.getMaxClassPeerWeek()){
          teacher.set(t.getTeacherName());
          totalClassesNumber.set(teacherNumberOfClasses.get());
        }
      });

      if(!teacher.get().equals("") && totalClassesNumber.get() > 25){
        var message = new MessageDTO(teacher.get(),Integer.toString(totalClassesNumber.get() - this.configPreferencesService.getMaxClassPeerWeek()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
      }


      this.configPreferencesService.setScheduleModel(datamodel);
      this.callScheduleRequestService.callRequest();
      this.configPreferencesService.setScheduleGenerated(true);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).body(this.configPreferencesService.getScheduleModel());
  }

  @PostMapping("/setScheduleSettings/{settings}/{biDate}/{triDate}/{semDate}")
  public ResponseEntity setScheduleType(
          @PathVariable(value = "settings")String settings,
          @PathVariable(value = "biDate")String biDate,
          @PathVariable(value = "triDate")String triDate,
          @PathVariable(value = "semDate")String semDate){

    var setting = settings.split(",");
    this.schoolSettingsRepository.updateDateSettings(biDate.replace("-","/"),
            triDate.replace("-","/"),
            semDate.replace("-", "/"));

    this.configPreferencesService.setMaxClassPeerWeek(Integer.parseInt(setting[0]));
    this.configPreferencesService.setMaxClassPerDay(Integer.parseInt(setting[1]));
    this.configPreferencesService.setMaxConsecutiveClass(Integer.parseInt(setting[2]));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/getScheduleSettings")
  public ResponseEntity getScheduleSettings(){
    List<Integer> scheduleSettings = new ArrayList<>();
    scheduleSettings.add(this.configPreferencesService.getMaxClassPeerWeek());
    scheduleSettings.add(this.configPreferencesService.getMaxClassPerDay());
    scheduleSettings.add(this.configPreferencesService.getMaxConsecutiveClass());
    return ResponseEntity.ok(scheduleSettings);
  }

  @GetMapping("/getSchedule")
  public ResponseEntity getSchedule() throws IOException {
    if(this.configPreferencesService.isScheduleGenerated()){
      return ResponseEntity.ok(this.callScheduleRequestService.convertExcelToJson());
    }
    return ResponseEntity.badRequest().body("Schedule doesnt exist.");
  }

  @DeleteMapping("/deleteSchedule")
  public ResponseEntity deleteSchedule(){
    this.configPreferencesService.setScheduleGenerated(false);
    return ResponseEntity.ok().build();
  }
}
