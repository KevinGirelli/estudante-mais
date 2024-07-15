package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class teacherSubject {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID teacherSubjectID;

  @Column(name = "subjectName",nullable = false)
  String subjectName;

  @ManyToOne
  teacher teacher;

  public teacherSubject(){super();}

  public teacherSubject(String subjectName, teacher teacher){
    this.subjectName = subjectName;
    this.teacher = teacher;
  }

  public UUID getTeacherSubjectID() {
    return teacherSubjectID;
  }

  public void setTeacherSubjectID(UUID teacherSubjectID) {
    this.teacherSubjectID = teacherSubjectID;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }
}
