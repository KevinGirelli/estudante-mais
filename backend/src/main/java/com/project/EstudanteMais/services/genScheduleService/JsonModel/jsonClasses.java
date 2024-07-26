package com.project.EstudanteMais.services.genScheduleService.JsonModel;

import java.util.ArrayList;
import java.util.List;

public class jsonClasses {
  List<String> classSchedule = new ArrayList<>();

  public jsonClasses(List<String> classSchedule) {
    this.classSchedule = classSchedule;
  }

  public List<String> getClassSchedule() {
    return classSchedule;
  }

  public void setClassSchedule(List<String> classSchedule) {
    this.classSchedule = classSchedule;
  }
}
