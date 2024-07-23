package com.project.EstudanteMais.services.notificationService;

public class notificationDTO {
  public String notificationID;
  public String name;
  public String recipientID;

  public notificationDTO() {
  }

  public notificationDTO(String notificationID, String name, String recipientID) {
    this.notificationID = notificationID;
    this.name = name;
    this.recipientID = recipientID;
  }

  public String getNotificationID() {
    return notificationID;
  }

  public void setNotificationID(String notificationID) {
    this.notificationID = notificationID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRecipientID() {
    return recipientID;
  }

  public void setRecipientID(String recipientID) {
    this.recipientID = recipientID;
  }
}
