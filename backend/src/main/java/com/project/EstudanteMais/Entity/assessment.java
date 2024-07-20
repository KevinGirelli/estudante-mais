package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class assessment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID assessmentID;

  @Column(name = "assessmentName",nullable = false)
  String assessmentName;

  @Column(name = "assessmentDate",nullable = false)
  Date assessmentDate;

  @ManyToOne
  classes classes;

  @ManyToOne
  teacher teacher;

  public assessment(){super();}

  public assessment(UUID assessmentID, String assessmentName, Date assessmentDate, classes classes, teacher teacher) {
    this.assessmentID = assessmentID;
    this.assessmentName = assessmentName;
    this.assessmentDate = assessmentDate;
    this.classes = classes;
    this.teacher = teacher;
  }

  public assessment(String assessmentName, Date assessmentDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher) {
    this.assessmentName = assessmentName;
    this.assessmentDate = assessmentDate;
    this.classes = classes;
    this.teacher = teacher;
  }

  public UUID getAssessmentID() {
    return assessmentID;
  }

  public void setAssessmentID(UUID assessmentID) {
    this.assessmentID = assessmentID;
  }

  public String getAssessmentName() {
    return assessmentName;
  }

  public void setAssessmentName(String assessmentName) {
    this.assessmentName = assessmentName;
  }

  public Date getAssessmentDate() {
    return assessmentDate;
  }

  public void setAssessmentDate(Date assessmentDate) {
    this.assessmentDate = assessmentDate;
  }

  public com.project.EstudanteMais.Entity.classes getClasses() {
    return classes;
  }

  public void setClasses(com.project.EstudanteMais.Entity.classes classes) {
    this.classes = classes;
  }

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }
}
