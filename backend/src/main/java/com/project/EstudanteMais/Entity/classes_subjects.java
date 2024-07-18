package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class classes_subjects {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID classSubjectID;

  @ManyToOne
  subjects subjects;

  @Column(name = "numberOfClasses")
  int numberOfClasses;

  @ManyToOne
  classes classes;
  public classes_subjects(){super();}

  public classes_subjects(UUID classSubjectID, subjects subjects, int numberOfClasses, classes classes) {
    this.classSubjectID = classSubjectID;
    this.subjects = subjects;
    this.numberOfClasses = numberOfClasses;
    this.classes = classes;
  }

  public classes_subjects(subjects subjects, int numberOfClasses, classes classes) {
    this.subjects = subjects;
    this.numberOfClasses = numberOfClasses;
    this.classes = classes;
  }

  public UUID getClassSubjectID() {
    return classSubjectID;
  }

  public void setClassSubjectID(UUID classSubjectID) {
    this.classSubjectID = classSubjectID;
  }

  public com.project.EstudanteMais.Entity.subjects getSubjects() {
    return subjects;
  }

  public void setSubjects(subjects subjects) {
    this.subjects = subjects;
  }

  public int getNumberOfClasses() {
    return numberOfClasses;
  }

  public void setNumberOfClasses(int numberOfClasses) {
    this.numberOfClasses = numberOfClasses;
  }

  public classes getClasses() {
    return classes;
  }

  public void setClasses(classes classes) {
    this.classes = classes;
  }
}
