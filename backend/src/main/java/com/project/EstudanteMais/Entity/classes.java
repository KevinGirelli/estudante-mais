package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class classes {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID classID;

  @Column(name = "className",nullable = false,unique = true)
  private String className;

  @Column(name = "gradeType",nullable = false)
  private String gradeType;

  public UUID getClassID() {
    return classID;
  }

  public void setClassID(UUID classID) {
    this.classID = classID;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getGradeType() {
    return gradeType;
  }

  public void setGradeType(String gradeType) {
    this.gradeType = gradeType;
  }

  public int getGradeNumber() {
    return gradeNumber;
  }

  public void setGradeNumber(int gradeNumber) {
    this.gradeNumber = gradeNumber;
  }

  @Column(name = "gradeNumber",nullable = false)
  private int gradeNumber;

  public classes(){}

  public classes(String className, String gradeType, int gradeNumber){
    this.className = className;
    this.gradeType = gradeType;
    this.gradeNumber = gradeNumber;
  }
}
