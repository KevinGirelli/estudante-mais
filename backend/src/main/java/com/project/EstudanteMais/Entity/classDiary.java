package com.project.EstudanteMais.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class classDiary {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID dayID;

  @ManyToOne
  subjects subjects;

  @Column(name = "dayContent")
  String dayContent;

  @Column(name = "observations")
  String observations;

  @Column(name = "registryDate")
  String registryDate;

  @ManyToOne
  classes classes;

  @ManyToOne
  teacher teacher;

  public classDiary(UUID dayID, com.project.EstudanteMais.Entity.subjects subjects, String dayContent, String observations, String registryDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher) {
    this.dayID = dayID;
    this.subjects = subjects;
    this.dayContent = dayContent;
    this.observations = observations;
    this.registryDate = registryDate;
    this.classes = classes;
    this.teacher = teacher;
  }

  public UUID getDayID() {
    return dayID;
  }

  public void setDayID(UUID dayID) {
    this.dayID = dayID;
  }

  public com.project.EstudanteMais.Entity.subjects getSubjects() {
    return subjects;
  }

  public void setSubjects(com.project.EstudanteMais.Entity.subjects subjects) {
    this.subjects = subjects;
  }

  public String getDayContent() {
    return dayContent;
  }

  public void setDayContent(String dayContent) {
    this.dayContent = dayContent;
  }

  public String getObservations() {
    return observations;
  }

  public void setObservations(String observations) {
    this.observations = observations;
  }

  public String getRegistryDate() {
    return registryDate;
  }

  public void setRegistryDate(String registryDate) {
    this.registryDate = registryDate;
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
}
