package com.project.EstudanteMais.controllers.CrisAssistant;

import com.project.EstudanteMais.Entity.attendenceStatus;
import com.project.EstudanteMais.Entity.messagesHistory;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs.*;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.genScheduleService.callScheduleRequestService;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/CrisAssistant")
public class crisController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  gradeRepository gradeRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @Autowired
  assessmentRepository assessmentRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  callScheduleRequestService callScheduleRequestService;

  @Autowired
  teacherClassesRepository teacherClassesRepository;

  @Autowired
  messageHistoryRepository messageHistoryRepository;

  @PostMapping("/validatePhone/{userID}")
  public ResponseEntity validatePhone(@PathVariable(value = "userID")String userID) throws IOException {
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(userID));
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(userID));

    if(getStudent != null){
      var phone = getStudent.getPhoneNumber();

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://127.0.0.1:5000/validatePhoneNumber/" + phone);
      var response = httpClient.execute(httpPost);

      if(response.getStatusLine().getStatusCode() != 200){
        return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok().build();
    }

    if(getTeacher != null){
      var phone = getTeacher.getPhoneNumber();

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://127.0.0.1:5000/validatePhoneNumber/" + phone);
      var response = httpClient.execute(httpPost);

      if(response.getStatusLine().getStatusCode() != 200){
        return ResponseEntity.badRequest().build();
      }

      return ResponseEntity.ok().build();
    }

    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getUserData/{phone}")
  public ResponseEntity getUserData(@PathVariable(value = "phone")String phone) throws IOException {
    var getTeacher = this.teacherRepository.findByphoneNumber(phone);
    var getStudent = this.studentRepository.findByphoneNumber(phone);

    if(getStudent != null){
      crisStudentDataModel userData = new crisStudentDataModel();
      setUserData(getStudent,userData);
      return ResponseEntity.ok(userData);
    }

    if(getTeacher != null){
      return ResponseEntity.ok(getTeacher);
    }

    return ResponseEntity.notFound().build();
  }

  public void setUserData(student getStudent,crisStudentDataModel userdata) throws IOException {
    userdata.studentID = getStudent.getStudentID().toString();
    userdata.studentName = getStudent.getStudentFullname();
    userdata.setStudentClass(getStudent.getClasses());

    //adicionar as provas do aluno
    var assessments = this.assessmentRepository.findByclasses(getStudent.getClasses());
    List<assessmentsDTO> tempAsses = new ArrayList<>();
    assessments.forEach(a -> {
      assessmentsDTO add = new assessmentsDTO();
      add.setNome_da_prova(a.getAssessmentName());
      add.setData_da_prova(a.getAssessmentDate());
      add.setProfessor(a.getTeacher().getTeacherName());
      add.setDisciplina(a.getSubjects().getSubjectname());

      var studentGrade = this.gradeRepository.findByAssessmentAndStudent(a, getStudent);
      if (studentGrade != null) {
        add.setNota_da_prova(studentGrade.getGradeValue().toString());
      } else {
        add.setNota_da_prova("Nota ainda não foi postada pelo professor.");
      }
      tempAsses.add(add);
    });
    userdata.setProvas_do_aluno(tempAsses);


    //Adicionar ao json as faltas do aluno
    var attendences = this.attendenceRepository.findBystudent(getStudent);
    List<attendanceDTO> attendance = new ArrayList<>();
    attendences.forEach(a -> {
      if (a.getStatus() == attendenceStatus.AUSENTE || a.getStatus() == attendenceStatus.JUSTIFICADO) {
        attendanceDTO add = new attendanceDTO();
        add.setMiss_date(a.getMissDate());
        add.setQuarter(a.getQuarter());
        add.setSubjectMissed(a.getSubjects().getSubjectname());
        add.setTeacher(a.getTeacher().getTeacherName());
        add.setNumber_of_missed_classes(a.getNumberOfClasses());
        add.setStatus(a.getStatus().getStatus());
        attendance.add(add);
      }
    });
    userdata.setStudentMissedClasses(attendance);

    //adicionar as materias da escola
    var allSubjects = this.subjectsRepository.findAll();
    userdata.allSchoolSubjects = allSubjects;

    var allTeachers = this.teacherSubjectRepository.findAll();
    List<UUID> ids = new ArrayList<>();
    messageModel allHistory = new messageModel();

    allTeachers.forEach(t -> {
      if (!ids.contains(t.getTeacher().getTeacherID())) {
        var chatHistory = this.messageHistoryRepository.findAllByRegistrationDate(getStudent.getClasses().getClassID(), t.getTeacher().getTeacherID());
        classChatHistory classHistory = new classChatHistory();
        List<messagedto> mensagens = new ArrayList<>();
        classHistory.setNome_da_turma(getStudent.getClasses().getClassName());
        classHistory.setProfessor(t.getTeacher().getTeacherName());

        chatHistory.forEach(c -> {
          messagedto message = new messagedto();
          message.setMensagem(c.getMessageText());
          message.setRemetente(c.getSenderName());
          message.setData_de_envio_mensagem(c.getRegistrationDate().toString());
          mensagens.add(message);
        });

        classHistory.setHistorico_de_mensagem(mensagens);
        var getHistory = allHistory.getTodos_os_chats();
        getHistory.add(classHistory);

        allHistory.setTodos_os_chats(getHistory);
      }
      ids.add(t.getTeacher().getTeacherID());
    });

    userdata.setHistorico_do_chat(allHistory);
    userdata.setHorario_das_aulas(this.callScheduleRequestService.convertExcelToJson());
  }
}
