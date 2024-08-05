package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class attendenceHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID registrationID;

  @Column(name = "registrationDate",nullable = false)
  public String registrationDate;

  @ManyToOne
  attendence missedClasses;

  public attendenceHistory(){super();}

  public attendenceHistory(String registrationDate, attendence missedClasses){
    this.registrationDate = registrationDate;
    this.missedClasses = missedClasses;
  }

  public UUID getRegistrationID() {
    return registrationID;
  }

  public void setRegistrationID(UUID registrationID) {
    this.registrationID = registrationID;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public attendence getMissedClasses() {
    return missedClasses;
  }

  public void setMissedClasses(attendence missedClasses) {
    this.missedClasses = missedClasses;
  }
}
