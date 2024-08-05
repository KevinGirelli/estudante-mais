package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class attendence {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID attendenceID;

  @ManyToOne
  student student;

  @ManyToOne
  classes classes;

  @ManyToOne
  teacher teacher;

  @Column(name = "status")
  attendenceStatus status;

  @Column(name = "quarter")
  int quarter;

  @ManyToOne
  subjects subjects;

  @Column(name = "numberOfClasses")
  int numberOfClasses;

  @Column(name = "missDate")
  String missDate;

  public attendence(){super();}

  public attendence(UUID attendenceID, com.project.EstudanteMais.Entity.student student, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher, attendenceStatus status, int quarter, com.project.EstudanteMais.Entity.subjects subjects, int numberOfClasses, String missDate) {
    this.attendenceID = attendenceID;
    this.student = student;
    this.classes = classes;
    this.teacher = teacher;
    this.status = status;
    this.quarter = quarter;
    this.subjects = subjects;
    this.numberOfClasses = numberOfClasses;
    this.missDate = missDate;
  }

  public attendence(com.project.EstudanteMais.Entity.student student, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher, attendenceStatus status, int quarter, com.project.EstudanteMais.Entity.subjects subjects, int numberOfClasses, String missDate) {
    this.student = student;
    this.classes = classes;
    this.teacher = teacher;
    this.status = status;
    this.quarter = quarter;
    this.subjects = subjects;
    this.numberOfClasses = numberOfClasses;
    this.missDate = missDate;
  }

  public com.project.EstudanteMais.Entity.subjects getSubjects() {
    return subjects;
  }

  public void setSubjects(com.project.EstudanteMais.Entity.subjects subjects) {
    this.subjects = subjects;
  }

  public int getQuarter() {
    return quarter;
  }

  public void setQuarter(int quarter) {
    this.quarter = quarter;
  }

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }

  public int getNumberOfClasses() {
    return numberOfClasses;
  }

  public void setNumberOfClasses(int numberOfClasses) {
    this.numberOfClasses = numberOfClasses;
  }

  public UUID getAttendenceID() {
    return attendenceID;
  }

  public void setAttendenceID(UUID attendenceID) {
    this.attendenceID = attendenceID;
  }

  public attendenceStatus getStatus() {
    return status;
  }

  public void setStatus(attendenceStatus status) {
    this.status = status;
  }

  public com.project.EstudanteMais.Entity.classes getClasses() {
    return classes;
  }

  public void setClasses(com.project.EstudanteMais.Entity.classes classes) {
    this.classes = classes;
  }

  public com.project.EstudanteMais.Entity.student getStudent() {
    return student;
  }

  public void setStudent(com.project.EstudanteMais.Entity.student student) {
    this.student = student;
  }

  public String getMissDate() {
    return missDate;
  }

  public void setMissDate(String missDate) {
    this.missDate = missDate;
  }
}
