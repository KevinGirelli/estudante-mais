package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class missedClassesHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID registrationID;

  @Column(name = "registrationDate",nullable = false)
  private Date registrationDate;

  @ManyToOne
  missedClasses missedClasses;

  public missedClassesHistory(){super();}

  public missedClassesHistory(Date registrationDate, missedClasses missedClasses){
    this.registrationDate = registrationDate;
    this.missedClasses = missedClasses;
  }

  public UUID getRegistrationID() {
    return registrationID;
  }

  public void setRegistrationID(UUID registrationID) {
    this.registrationID = registrationID;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public com.project.EstudanteMais.Entity.missedClasses getMissedClasses() {
    return missedClasses;
  }

  public void setMissedClasses(com.project.EstudanteMais.Entity.missedClasses missedClasses) {
    this.missedClasses = missedClasses;
  }
}
