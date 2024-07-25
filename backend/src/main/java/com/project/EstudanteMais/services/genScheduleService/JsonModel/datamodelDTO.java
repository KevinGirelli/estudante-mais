package com.project.EstudanteMais.services.genScheduleService.JsonModel;

import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.classDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.settingsDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectGroupDTO;

import java.util.ArrayList;
import java.util.List;

public class datamodelDTO {
  List<classDTO> classes = new ArrayList<>();
  List<subjectGroupDTO> subjectGroup = new ArrayList<>();
  settingsDTO settings;

  public datamodelDTO(){}

  public datamodelDTO(List<classDTO> classes, List<subjectGroupDTO> subjectGroup, settingsDTO settings) {
    this.classes = classes;
    this.subjectGroup = subjectGroup;
    this.settings = settings;
  }

  public List<classDTO> getClasses() {
    return classes;
  }

  public void setClasses(List<classDTO> classes) {
    this.classes = classes;
  }

  public List<subjectGroupDTO> getSubjectGroup() {
    return subjectGroup;
  }

  public void setSubjectGroup(List<subjectGroupDTO> subjectGroup) {
    this.subjectGroup = subjectGroup;
  }

  public settingsDTO getSettings() {
    return settings;
  }

  public void setSettings(settingsDTO settings) {
    this.settings = settings;
  }
}
