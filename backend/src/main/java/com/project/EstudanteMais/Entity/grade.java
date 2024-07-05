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
  private teacher teacher;

  @Column(name = "registrationDate",nullable = false)
  private Date date;

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

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getQuarter() {
    return quarter;
  }

  public void setQuarter(int quarter) {
    this.quarter = quarter;
  }
}
