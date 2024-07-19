package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class teacherSubject {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID teacherSubjectID;

  @ManyToOne
  subjects subject;

  @ManyToOne
  teacher teacher;

  public teacherSubject(){super();}

  public teacherSubject(subjects subject, teacher teacher) {
    this.subject = subject;
    this.teacher = teacher;
  }

  public teacherSubject(UUID teacherSubjectID, subjects subject, com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacherSubjectID = teacherSubjectID;
    this.subject = subject;
    this.teacher = teacher;
  }

  public UUID getTeacherSubjectID() {
    return teacherSubjectID;
  }

  public void setTeacherSubjectID(UUID teacherSubjectID) {
    this.teacherSubjectID = teacherSubjectID;
  }

  public subjects getSubject() {
    return subject;
  }

  public void setSubject(subjects subject) {
    this.subject = subject;
  }

  public com.project.EstudanteMais.Entity.teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(com.project.EstudanteMais.Entity.teacher teacher) {
    this.teacher = teacher;
  }
}
