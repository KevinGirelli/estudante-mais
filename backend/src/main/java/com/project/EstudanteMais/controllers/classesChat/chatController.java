package com.project.EstudanteMais.controllers.classesChat;

import com.project.EstudanteMais.Entity.dto.messageModel;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class chatController {

  @MessageMapping("/chatroom/{classID}")
  private messageModel sendMessageToClass(@DestinationVariable String classID, @Payload messageModel message){
    return message;
  }
}
