package com.project.EstudanteMais.controllers.classesChat;

import com.project.EstudanteMais.Entity.dto.messageModel;
import com.project.EstudanteMais.Entity.messagesHistory;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.messageHistoryRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "https://native-randomly-swift.ngrok-free.app")
public class chatController {
  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  messageHistoryRepository messageHistoryRepository;

  @MessageMapping("/sendMessage/{teacherID}/{classID}")
  @SendTo("/chatroom/{teacherID}/{classID}")
  public messageModel sendMessageToClass(@DestinationVariable String teacherID, @DestinationVariable String classID, @Payload messageModel message){
    var getClass = this.classesRepository.findByclassID(UUID.fromString(classID));
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherID));

    if(getTeacher != null && getClass != null){
      messagesHistory newMessage = new messagesHistory(
              message.getMessage(),
              message.getSenderName(),
              LocalDateTime.now(),
              getClass,
              getTeacher
      );

      this.messageHistoryRepository.save(newMessage);
    }
    return message;
  }

  @GetMapping("/getMessageHistory/{classAndTeacherIDS}")
  public ResponseEntity getMessageHistory(@PathVariable(value = "classAndTeacherIDS")String ids){
    var split = ids.split(",");

    var getClass = this.classesRepository.findByclassID(UUID.fromString(split[0]));
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(split[1]));

    if(getClass != null && getTeacher != null){
      var history = this.messageHistoryRepository.findAllByRegistrationDate(getClass.getClassID(),getTeacher.getTeacherID());
      if(history != null){
        return ResponseEntity.ok(history);
      }
    }
    return ResponseEntity.internalServerError().build();
  }
}
