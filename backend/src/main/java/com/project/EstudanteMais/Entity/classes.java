package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class classes {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID classID;

  @Column(name = "className",nullable = false)
  private String className;

  @Column(name = "gradeType",nullable = false)
  private String gradeType;

  @Column(name = "gradeNumber",nullable = false)
  private int gradeNumber;
}
