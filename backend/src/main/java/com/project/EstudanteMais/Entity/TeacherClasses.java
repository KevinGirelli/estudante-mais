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
  public TeacherClasses(){}

  public TeacherClasses(teacher teacher, classes classes){
    this.teacher = teacher;
    this.classes = classes;
  }
}
