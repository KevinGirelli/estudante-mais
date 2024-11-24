package com.project.EstudanteMais.controllers.student;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.attendenceStatus;
import com.project.EstudanteMais.Entity.dto.boletim.boletimDTO;
import com.project.EstudanteMais.Entity.dto.boletim.subjectBoletim;
import com.project.EstudanteMais.Entity.dto.returnAssessmentDTO;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.excelToJsonModel;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/student")
public class studentController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  callScheduleRequestService callScheduleRequestService;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  gradeRepository gradeRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @Autowired
  classes_subjectsRepository classesSubjectsRepository;

  @GetMapping("/getAssessments/{studentID}")
  public ResponseEntity getAssessments(@PathVariable(value = "studentID")String studentID){
      var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentID));
      if(getStudent != null){
        List<assessment> allAssess = this.assessmentRepository.findByclasses(getStudent.getClasses());
        List<returnAssessmentDTO> allAsessFilter = new ArrayList<>();
        allAssess.forEach(assess ->{
            returnAssessmentDTO add = new returnAssessmentDTO(
                    assess.getAssessmentID().toString(),
                    assess.getAssessmentName(),
                    assess.getClasses().getClassName(),
                    assess.getSubjects().getSubjectname(),
                    assess.getAssessmentDate(),
                    assess.getClasses().getClassID().toString(),
                    assess.getSubjects().getSubjectID().toString(),
                    assess.getTeacher().getTeacherName(),
                    assess.getTeacher().getTeacherID().toString()
            );
            allAsessFilter.add(add);
        });
        return ResponseEntity.ok(allAsessFilter);
      }else{
        return ResponseEntity.internalServerError().build();
      }
  }

  @GetMapping("/getSubjectsFromClasses/{studentID}")
  public ResponseEntity getSubjectFromClass(@PathVariable(value = "studentID")String id){
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(id));
    List<String> subjects = new ArrayList<>();
    if(getStudent != null){
      var getClass = this.classesRepository.findByclassID(getStudent.getClasses().getClassID());
      var Classessubjects = this.classesSubjectsRepository.findByclasses(getClass);
      Classessubjects.forEach(s ->{
        String add = s.getSubjects().getSubjectID().toString() + "," + s.getSubjects().getSubjectname() + "," + s.getSubjects().getSubjectPeriod();
        subjects.add(add);
      });

      return ResponseEntity.ok(subjects);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/viewSchedule/{classID}")
  public ResponseEntity viewStudentClassSchedule(@PathVariable(value = "classID")String classID) throws IOException {
    if(this.configPreferencesService.isScheduleGenerated()){
      var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
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
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/generateBoletim/{studentID}/{period}/{type}")
  public ResponseEntity genBoletim(@PathVariable(value = "studentID")String studentID,
                                   @PathVariable(value = "period")String period,
                                   @PathVariable(value = "type")String type){

    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(studentID));

    if(getStudent != null){
      var getSubjects = this.subjectsRepository.findByPeriodType(Integer.parseInt(type));

      List<subjectBoletim> subjectsNotas = new ArrayList<>();
      var getStudentGrades = this.gradeRepository.findBystudent(getStudent);

      getSubjects.forEach(subject ->{
        List<String> gradesMedia = new ArrayList<>();
        List<String> absences = new ArrayList<>();
        AtomicReference<Float> finalAverage = new AtomicReference<>((float) 0);
        AtomicInteger totalAbsences = new AtomicInteger(0);

        //For passando pela quantidadade de periodos
        for(int i = 1; i <= Integer.parseInt(period); i++){
          AtomicInteger qtd = new AtomicInteger(0);
          AtomicReference<Float> soma = new AtomicReference<>((float) 0);
          AtomicInteger index = new AtomicInteger(i);

          //Pegar todas as notas do aluno com base no trimestre
          getStudentGrades.forEach(studentGrade ->{
            if(studentGrade.getAssessment().getSubjects().getSubjectID().toString().equals(subject.getSubjectID().toString()) && (studentGrade.getQuarter() == index.get())){
              qtd.addAndGet(1);
              soma.set(soma.get() + studentGrade.getGradeValue());
            }
          });
          var media = soma.get() / qtd.get();

          if(!Float.isNaN(media)){
            gradesMedia.add(Float.toString(media));
          }else{
            gradesMedia.add("0");
          }


          //Pegar o numero total de faltas do aluno no trimestre
          var getStudentAttendence = this.attendenceRepository.findBysubjectsAndQuarterAndStudent(subject,index.get(),getStudent);
          AtomicInteger absencesValue = new AtomicInteger();

          getStudentAttendence.forEach(studentAttendence ->{
            if(studentAttendence.getStatus() == attendenceStatus.AUSENTE){
              absencesValue.addAndGet(studentAttendence.getNumberOfClasses());
            }
          });
          absences.add(Integer.toString(absencesValue.get()));
          totalAbsences.addAndGet(absencesValue.get());

          gradesMedia.forEach(g ->{
            finalAverage.set(Float.parseFloat(g) + finalAverage.get());
          });

          finalAverage.set(finalAverage.get() / gradesMedia.size());
        }

        //Criar objeto subjectBoletim
        subjectBoletim newSubjectBoletim = new subjectBoletim(subject.getSubjectname(),gradesMedia,absences,finalAverage.get(),totalAbsences.get());
        subjectsNotas.add(newSubjectBoletim);
      });

      //Pegar quantidade de aulas para calcular frequencia
      var amountOfClasses = this.classesSubjectsRepository.findByclasses(getStudent.getClasses());
      AtomicInteger amount = new AtomicInteger(0);

      amountOfClasses.forEach(classes ->{
        amount.addAndGet(classes.getNumberOfClasses());
      });

      boletimDTO newBoletim = new boletimDTO(
              getStudent.getStudentFullname(),
              getStudent.getClasses().getType().toString(),
              getStudent.getClasses().getClassName(),
              Integer.toString(getStudent.getClasses().getGradeNumber()) + "° Ano" + " - " + getStudent.getClasses().getGradeType(),
              Integer.toString(LocalDate.now().getYear()),
              subjectsNotas,
              amount.get()
      );

      return ResponseEntity.ok(newBoletim);

    }
    return ResponseEntity.badRequest().build();
  }
}
