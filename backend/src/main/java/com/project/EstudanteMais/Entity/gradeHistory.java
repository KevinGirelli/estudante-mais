package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class gradeHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID registrationID;

  @Column(name = "registrationDate",nullable = false)
  private Date registrationDate;

  @ManyToOne
  private grade grade;

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

  public com.project.EstudanteMais.Entity.grade getGrade() {
    return grade;
  }

  public void setGrade(com.project.EstudanteMais.Entity.grade grade) {
    this.grade = grade;
  }
}
