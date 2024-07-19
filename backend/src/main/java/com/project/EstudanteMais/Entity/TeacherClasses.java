package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class TeacherClasses {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID tcID;

  @ManyToOne
  teacher teacher;

  @ManyToOne
  classes classes;

  @ManyToOne
  subjects subjects;
  public TeacherClasses(){}

  public TeacherClasses(teacher teacher, classes classes, subjects subjects) {
    this.teacher = teacher;
    this.classes = classes;
    this.subjects = subjects;
  }

  public TeacherClasses(UUID tcID, teacher teacher, classes classes, subjects subjects) {
    this.tcID = tcID;
    this.teacher = teacher;
    this.classes = classes;
    this.subjects = subjects;
  }

  public UUID getTcID() {
    return tcID;
  }

  public void setTcID(UUID tcID) {
    this.tcID = tcID;
  }

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }

  public com.project.EstudanteMais.Entity.classes getClasses() {
    return classes;
  }

  public void setClasses(com.project.EstudanteMais.Entity.classes classes) {
    this.classes = classes;
  }

  public com.project.EstudanteMais.Entity.subjects getSubjects() {
    return subjects;
  }

  public void setSubjects(com.project.EstudanteMais.Entity.subjects subjects) {
    this.subjects = subjects;
  }
}
