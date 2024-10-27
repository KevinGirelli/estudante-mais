package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class subjects {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID subjectID;

  @Column(name = "subjectName",nullable = false,unique = true)
  String subjectname;

  @Column(name = "subjectPeriod",nullable = false)
  int subjectPeriod;

  @Column(name = "maxGrades",nullable = false)
  int maxGrades;

  public subjects(){}

  public subjects(UUID subjectID, String subjectname, int subjectPeriod, int maxGrades) {
    this.subjectID = subjectID;
    this.subjectname = subjectname;
    this.subjectPeriod = subjectPeriod;
    this.maxGrades = maxGrades;
  }

  public subjects(String subjectname,int subjectPeriod,int maxGrades) {
    this.subjectname = subjectname;
    this.subjectPeriod = subjectPeriod;
    this.maxGrades = maxGrades;
  }

  public int getSubjectPeriod() {
    return subjectPeriod;
  }

  public void setSubjectPeriod(int subjectPeriod) {
    this.subjectPeriod = subjectPeriod;
  }

  public int getMaxGrades() {
    return maxGrades;
  }

  public void setMaxGrades(int maxGrades) {
    this.maxGrades = maxGrades;
  }

  public UUID getSubjectID() {
    return subjectID;
  }

  public void setSubjectID(UUID subjectID) {
    this.subjectID = subjectID;
  }

  public String getSubjectname() {
    return subjectname;
  }

  public void setSubjectname(String subjectname) {
    this.subjectname = subjectname;
  }

}
