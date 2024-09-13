package com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs;

public class attendanceDTO {
  String miss_date;
  int number_of_missed_classes;
  int quarter;
  String subjectMissed;
  String teacher;

  public String getMiss_date() {
    return miss_date;
  }

  public void setMiss_date(String miss_date) {
    this.miss_date = miss_date;
  }

  public int getNumber_of_missed_classes() {
    return number_of_missed_classes;
  }

  public void setNumber_of_missed_classes(int number_of_missed_classes) {
    this.number_of_missed_classes = number_of_missed_classes;
  }

  public int getQuarter() {
    return quarter;
  }

  public void setQuarter(int quarter) {
    this.quarter = quarter;
  }

  public String getSubjectMissed() {
    return subjectMissed;
  }

  public void setSubjectMissed(String subjectMissed) {
    this.subjectMissed = subjectMissed;
  }

  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }
}
