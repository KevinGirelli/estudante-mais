package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class missedClasses {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID missedID;

  @ManyToOne
  student student;

  @ManyToOne
  teacherSubject teacherSubject;

  @Column(name = "missDate")
  Date missDate;

  public missedClasses(){super();}

  public missedClasses(teacherSubject teacherSubject, student student, Date missDate){
    this.teacherSubject = teacherSubject;
    this.student = student;
    this.missDate = missDate;
  }

  public com.project.EstudanteMais.Entity.student getStudent() {
    return student;
  }

  public void setStudent(com.project.EstudanteMais.Entity.student student) {
    this.student = student;
  }

  public UUID getMissedID() {
    return missedID;
  }

  public void setMissedID(UUID missedID) {
    this.missedID = missedID;
  }

  public com.project.EstudanteMais.Entity.teacherSubject getTeacherSubject() {
    return teacherSubject;
  }

  public void setTeacherSubject(com.project.EstudanteMais.Entity.teacherSubject teacherSubject) {
    this.teacherSubject = teacherSubject;
  }

  public Date getMissDate() {
    return missDate;
  }

  public void setMissDate(Date missDate) {
    this.missDate = missDate;
  }
}
