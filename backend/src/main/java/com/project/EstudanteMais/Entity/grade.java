package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class grade {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID gradeID;

  @Column(name = "gradeValue",nullable = false)
  private Float gradeValue;

  @ManyToOne
  assessment assessment;

  @ManyToOne
  private student student;

  public com.project.EstudanteMais.Entity.assessment getAssessment() {
    return assessment;
  }

  public void setAssessment(com.project.EstudanteMais.Entity.assessment assessment) {
    this.assessment = assessment;
  }

  public com.project.EstudanteMais.Entity.student getStudent() {
    return student;
  }

  public void setStudent(com.project.EstudanteMais.Entity.student student) {
    this.student = student;
  }

  @Column(name = "registrationDate",nullable = false)
  private String date;

  @Column(name = "quarter",nullable = false)
  int quarter;


  public UUID getGradeID() {
    return gradeID;
  }

  public void setGradeID(UUID gradeID) {
    this.gradeID = gradeID;
  }

  public Float getGradeValue() {
    return gradeValue;
  }

  public void setGradeValue(Float gradeValue) {
    this.gradeValue = gradeValue;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getQuarter() {
    return quarter;
  }

  public void setQuarter(int quarter) {
    this.quarter = quarter;
  }

  public grade(){super();}

  public grade(UUID gradeID, Float gradeValue, com.project.EstudanteMais.Entity.assessment assessment, com.project.EstudanteMais.Entity.student student, String date, int quarter) {
    this.gradeID = gradeID;
    this.gradeValue = gradeValue;
    this.assessment = assessment;
    this.student = student;
    this.date = date;
    this.quarter = quarter;
  }

  public grade(Float gradeValue, com.project.EstudanteMais.Entity.assessment assessment, com.project.EstudanteMais.Entity.student student, String date, int quarter) {
    this.gradeValue = gradeValue;
    this.assessment = assessment;
    this.student = student;
    this.date = date;
    this.quarter = quarter;
  }
}
