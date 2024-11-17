package com.project.EstudanteMais.services;

import com.project.EstudanteMais.Entity.periodType;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.datamodelDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class configPreferencesService {
  //General system config
  private List<String> activeCodes = new ArrayList<String>();

  private datamodelDTO scheduleModel = new datamodelDTO();

  //scheduleGen config

  private int maxConsecutiveClass = 2;

  private int MaxClassPerDay = 5;

  private int maxClassPeerWeek = 25;


  //Admin preferences config
  private boolean IsScheduleGenerated = false;

  public int getMaxConsecutiveClass() {
    return maxConsecutiveClass;
  }

  public void setMaxConsecutiveClass(int maxConsecutiveClass) {
    this.maxConsecutiveClass = maxConsecutiveClass;
  }

  public int getMaxClassPerDay() {
    return MaxClassPerDay;
  }

  public void setMaxClassPerDay(int maxClassPerDay) {
    MaxClassPerDay = maxClassPerDay;
  }

  public int getMaxClassPeerWeek() {
    return maxClassPeerWeek;
  }

  public void setMaxClassPeerWeek(int maxClassPeerWeek) {
    this.maxClassPeerWeek = maxClassPeerWeek;
  }

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

}
