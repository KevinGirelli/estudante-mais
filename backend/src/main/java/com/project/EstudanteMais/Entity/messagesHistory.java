package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class messagesHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID messageID;

  @Column(name = "messageText")
  String messageText;

  @Column(name = "senderName")
  String senderName;

  @Column(name = "registrationDate")
  LocalDateTime registrationDate;

  @ManyToOne
  classes classes;

  @ManyToOne
  teacher teacher;

  public messagesHistory(){}

  public messagesHistory(UUID messageID, String messageText, String senderName, LocalDateTime registrationDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher) {
    this.messageID = messageID;
    this.messageText = messageText;
    this.senderName = senderName;
    this.registrationDate = registrationDate;
    this.classes = classes;
    this.teacher = teacher;
  }

  public messagesHistory(String messageText, String senderName, LocalDateTime registrationDate, com.project.EstudanteMais.Entity.classes classes, com.project.EstudanteMais.Entity.teacher teacher) {
    this.messageText = messageText;
    this.senderName = senderName;
    this.registrationDate = registrationDate;
    this.classes = classes;
    this.teacher = teacher;
  }

  public UUID getMessageID() {
    return messageID;
  }

  public void setMessageID(UUID messageID) {
    this.messageID = messageID;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
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
