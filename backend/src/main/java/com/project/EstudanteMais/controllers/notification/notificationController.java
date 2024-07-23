package com.project.EstudanteMais.controllers.notification;

import com.project.EstudanteMais.services.notificationService.notificationDTO;
import com.project.EstudanteMais.services.notificationService.notificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class notificationController {

  @Autowired
  notificationManager notificationManager;

  @GetMapping("/getNotifications/{recipientID}")
  public ResponseEntity getNotification(@PathVariable(value = "recipientID")String recipientID){
    List<notificationDTO> notification = this.notificationManager.getNotifications(recipientID);;
    if(notification != null){
      return ResponseEntity.ok(notification);
    }
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/readNotification/{notificationID}")
  public ResponseEntity readNotification(@PathVariable(value = "notificationID")String id){
    this.notificationManager.deletingNotification(id);
    return ResponseEntity.ok().build();
  }
}
