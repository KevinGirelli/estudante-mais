package com.project.EstudanteMais.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class configPreferencesService {
  //General system config
  private List<String> activeCodes = new ArrayList<String>();


  //Admin preferences config

  private int currentQuarterType = 1;
  private int quarterType = 3;
  private boolean enableQuarterRegistration = false;

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
