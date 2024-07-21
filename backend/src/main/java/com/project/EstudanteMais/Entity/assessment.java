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
  String assessmentDate;

  @ManyToOne
  classes classes;

  @ManyToOne
  teacher teacher;

  @ManyToOne
  subjects subjects;

  public assessment(){super();}

  public assessment(String assessmentName, String assessmentDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher, com.project.EstudanteMais.Entity.subjects subjects) {
    this.assessmentName = assessmentName;
    this.assessmentDate = assessmentDate;
    this.classes = classes;
    this.teacher = teacher;
    this.subjects = subjects;
  }

  public assessment(UUID assessmentID, String assessmentName, String assessmentDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher, com.project.EstudanteMais.Entity.subjects subjects) {
    this.assessmentID = assessmentID;
    this.assessmentName = assessmentName;
    this.assessmentDate = assessmentDate;
    this.classes = classes;
    this.teacher = teacher;
    this.subjects = subjects;
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

  public String getAssessmentDate() {
    return assessmentDate;
  }

  public void setAssessmentDate(String assessmentDate) {
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

  public com.project.EstudanteMais.Entity.subjects getSubjects() {
    return subjects;
  }

  public void setSubjects(com.project.EstudanteMais.Entity.subjects subjects) {
    this.subjects = subjects;
  }
}
