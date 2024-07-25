package com.project.EstudanteMais.services;

import com.project.EstudanteMais.services.genScheduleService.JsonModel.datamodelDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class configPreferencesService {
  //General system config
  private List<String> activeCodes = new ArrayList<String>();

  private datamodelDTO scheduleModel = new datamodelDTO();



  //Admin preferences config
  private boolean IsScheduleGenerated = false;

  private int currentQuarterType = 1;
  private int quarterType = 3;
  private boolean enableQuarterRegistration = false;

  public datamodelDTO getScheduleModel() {
    return scheduleModel;
  }

  public void setScheduleModel(datamodelDTO scheduleModel) {
    this.scheduleModel = scheduleModel;
  }

  public boolean isScheduleGenerated() {
    return IsScheduleGenerated;
  }

  public void setScheduleGenerated(boolean scheduleGenerated) {
    IsScheduleGenerated = scheduleGenerated;
  }

  public List<String> getActiveCodes() {
    return activeCodes;
  }

  public void setActiveCodes(List<String> activeCodes) {
    this.activeCodes = activeCodes;
  }

  public int getQuarterType() {
    return quarterType;
  }

  public void setQuarterType(int quarterType) {
    this.quarterType = quarterType;
  }

  public boolean isEnableQuarterRegistration() {
    return enableQuarterRegistration;
  }

  public int getCurrentQuarterType() {
    return currentQuarterType;
  }

  public void setCurrentQuarterType(int currentQuarterType) {
    this.currentQuarterType = currentQuarterType;
  }

  public void setEnableQuarterRegistration(boolean enableQuarterRegistration) {
    this.enableQuarterRegistration = enableQuarterRegistration;
  }
}
