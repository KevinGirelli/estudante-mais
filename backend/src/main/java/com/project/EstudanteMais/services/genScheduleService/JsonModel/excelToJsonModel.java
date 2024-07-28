package com.project.EstudanteMais.services.genScheduleService.JsonModel;

import java.util.ArrayList;
import java.util.List;

public class excelToJsonModel {
    List<String> hours = new ArrayList<>();
    List<jsonClasses> classes = new ArrayList<>();

  public excelToJsonModel(){}

  public excelToJsonModel(List<String> hours, List<jsonClasses> classes) {
    this.hours = hours;
    this.classes = classes;
  }

  public List<String> getHours() {
    return hours;
  }

  public void setHours(List<String> hours) {
    this.hours = hours;
  }

  public List<jsonClasses> getClasses() {
    return classes;
  }

  public void setClasses(List<jsonClasses> classes) {
    this.classes = classes;
  }
}
