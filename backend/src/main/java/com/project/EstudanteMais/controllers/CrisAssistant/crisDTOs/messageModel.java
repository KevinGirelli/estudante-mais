package com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs;

import java.util.ArrayList;
import java.util.List;

public class messageModel {
  List<classChatHistory> todos_os_chats = new ArrayList<>();

  public List<classChatHistory> getTodos_os_chats() {
    return todos_os_chats;
  }

  public void setTodos_os_chats(List<classChatHistory> todos_os_chats) {
    this.todos_os_chats = todos_os_chats;
  }
}
