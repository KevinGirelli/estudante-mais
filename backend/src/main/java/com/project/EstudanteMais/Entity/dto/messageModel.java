package com.project.EstudanteMais.Entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class messageModel {
  private String senderName;
  private String reciverName;
  private String message;
  private String date;

  public messageModel(){}

  public messageModel(String senderName, String reciverName, String message, String date) {
    this.senderName = senderName;
    this.reciverName = reciverName;
    this.message = message;
    this.date = date;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public String getReciverName() {
    return reciverName;
  }

  public void setReciverName(String reciverName) {
    this.reciverName = reciverName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
