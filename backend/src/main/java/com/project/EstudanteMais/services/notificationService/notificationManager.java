package com.project.EstudanteMais.services.notificationService;

import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.dto.studentDataDTO;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.services.UUIDformatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class notificationManager {

  @Autowired
  UUIDformatter uuiDformatter;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;
  private List<notificationDTO> notificationsRequests = new ArrayList<>();

  public void createNotificationForStudents(classes classes, notificationDTO notification){
    List<studentDataDTO> students = this.studentRepository.getAllStudentFromClass(classes.getClassID());
    students.forEach(s ->{
      notification.setNotificationID(uuiDformatter.formatUuid(UUID.randomUUID()));
      notification.setRecipientID(uuiDformatter.formatUuid(s.getStudentID()));
      this.CreateNotification(notification);
    });
    this.notificationsRequests.forEach(n ->{
      System.out.println(n.getRecipientID());
    });
  }

  public String CreateNotification(notificationDTO newNotification){
    this.notificationsRequests.forEach(n ->{
      if(n.getNotificationID().equals(newNotification.getNotificationID())){
        newNotification.setNotificationID(this.uuiDformatter.formatUuid(UUID.randomUUID()));
      }
    });
    this.notificationsRequests.add(newNotification);
    return "ERROR ON REGISTRING NOTIFICATION";
  }

  public List<notificationDTO> getNotifications(String recipientID){
    List<notificationDTO> ToReturn = new ArrayList<>();
    this.notificationsRequests.forEach(n ->{
      if(n.getRecipientID().equals(recipientID)){
        notificationDTO add = new notificationDTO();
        add.setNotificationID(n.getNotificationID());
        add.setName(n.getName());
        add.setRecipientID(n.getRecipientID());
        ToReturn.add(add);
      }
    });
    return ToReturn;
  }

  public Boolean deletingNotification(String notificationID){
    AtomicBoolean removed = new AtomicBoolean(false);
    this.notificationsRequests.forEach(n ->{
      if(n.getNotificationID().equals(notificationID)){
        this.notificationsRequests.remove(n);
        removed.set(true);
      }
    });

    if(removed.get()){
      return true;
    }
    return false;
  }
}
