package com.project.EstudanteMais.services;

import org.springframework.stereotype.Service;

@Service
public class configPreferencesService {
  //General system config
  private boolean isClassesChanged = false;


  //Admin preferences config
  private int quarterType = 3;
  private boolean enableQuarterRegistration = false;

  public boolean isClassesChanged() {
    return isClassesChanged;
  }

  public void setClassesChanged(boolean classesChanged) {
    isClassesChanged = classesChanged;
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

  public void setEnableQuarterRegistration(boolean enableQuarterRegistration) {
    this.enableQuarterRegistration = enableQuarterRegistration;
  }
}
