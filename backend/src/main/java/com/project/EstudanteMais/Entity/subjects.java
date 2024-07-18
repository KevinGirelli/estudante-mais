package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class subjects {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID subjectID;

  @Column(name = "subjectName",nullable = false,unique = true)
  String subjectname;

  public subjects(){super();}

  public subjects(UUID subjectID, String subjectname) {
    this.subjectID = subjectID;
    this.subjectname = subjectname;
  }

  public subjects(String subjectname) {
    this.subjectname = subjectname;
  }

  public UUID getSubjectID() {
    return subjectID;
  }

  public void setSubjectID(UUID subjectID) {
    this.subjectID = subjectID;
  }

  public String getSubjectname() {
    return subjectname;
  }

  public void setSubjectname(String subjectname) {
    this.subjectname = subjectname;
  }

}
