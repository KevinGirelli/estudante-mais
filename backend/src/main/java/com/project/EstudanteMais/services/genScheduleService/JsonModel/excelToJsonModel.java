package com.project.EstudanteMais.services.genScheduleService.JsonModel;

import java.util.ArrayList;
import java.util.List;

public class excelToJsonModel {
    List<String> hours = new ArrayList<>();
    List<String> classes = new ArrayList<>();
    List<String> schedule = new ArrayList<>();

  public excelToJsonModel(){}
  public excelToJsonModel(List<String> hours, List<String> classes, List<String> schedule) {
    this.hours = hours;
    this.classes = classes;
    this.schedule = schedule;
  }

  public List<String> getHours() {
    return hours;
  }

  public void setHours(List<String> hours) {
    this.hours = hours;
  }

  public List<String> getClasses() {
    return classes;
  }

  public void setClasses(List<String> classes) {
    this.classes = classes;
  }

  public List<String> getSchedule() {
    return schedule;
  }

  public void setSchedule(List<String> schedule) {
    this.schedule = schedule;
  }
}
