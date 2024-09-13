package com.project.EstudanteMais.controllers.CrisAssistant;
import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs.assessmentsDTO;
import com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs.attendanceDTO;
import com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs.messageModel;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.excelToJsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class crisStudentDataModel {
  public String studentID = "";
  public String studentName;
  public classes studentClass;
  public List<assessmentsDTO> provas_do_aluno = new ArrayList<>();

  public List<attendanceDTO> studentMissedClasses = new ArrayList<>();

  public List<subjects> allSchoolSubjects = new ArrayList<>();

  List<String> teachers = new ArrayList<>();

  messageModel historico_do_chat = new messageModel();

  excelToJsonModel horario_das_aulas;

  public crisStudentDataModel(){}

  public crisStudentDataModel(String studentID, String studentName, classes studentClass, List<assessmentsDTO> provas_do_aluno, List<attendanceDTO> studentMissedClasses, List<subjects> allSchoolSubjects, List<String> teachers, messageModel historico_do_chat, excelToJsonModel horario_das_aulas) {
    this.studentID = studentID;
    this.studentName = studentName;
    this.studentClass = studentClass;
    this.provas_do_aluno = provas_do_aluno;
    this.studentMissedClasses = studentMissedClasses;
    this.allSchoolSubjects = allSchoolSubjects;
    this.teachers = teachers;
    this.historico_do_chat = historico_do_chat;
    this.horario_das_aulas = horario_das_aulas;
  }

  public String getStudentID() {
    return studentID;
  }

  public void setStudentID(String studentID) {
    this.studentID = studentID;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public classes getStudentClass() {
    return studentClass;
  }

  public void setStudentClass(classes studentClass) {
    this.studentClass = studentClass;
  }

  public List<assessmentsDTO> getProvas_do_aluno() {
    return provas_do_aluno;
  }

  public void setProvas_do_aluno(List<assessmentsDTO> provas_do_aluno) {
    this.provas_do_aluno = provas_do_aluno;
  }

  public List<attendanceDTO> getStudentMissedClasses() {
    return studentMissedClasses;
  }

  public void setStudentMissedClasses(List<attendanceDTO> studentMissedClasses) {
    this.studentMissedClasses = studentMissedClasses;
  }

  public List<subjects> getAllSchoolSubjects() {
    return allSchoolSubjects;
  }

  public void setAllSchoolSubjects(List<subjects> allSchoolSubjects) {
    this.allSchoolSubjects = allSchoolSubjects;
  }

  public List<String> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<String> teachers) {
    this.teachers = teachers;
  }

  public messageModel getHistorico_do_chat() {
    return historico_do_chat;
  }

  public void setHistorico_do_chat(messageModel historico_do_chat) {
    this.historico_do_chat = historico_do_chat;
  }

  public excelToJsonModel getHorario_das_aulas() {
    return horario_das_aulas;
  }

  public void setHorario_das_aulas(excelToJsonModel horario_das_aulas) {
    this.horario_das_aulas = horario_das_aulas;
  }
}
