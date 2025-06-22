package com.example.chat_runtime.controller;

import com.example.chat_runtime.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  @MessageMapping("/sendMessage")
  @SendTo("topic/messages")
   public ChatMessage sendMessage(ChatMessage message) {
      System.out.println(" Nhan tin nhan : " + message);
      return message;
  }

  @MessageMapping("/leave")
  @SendTo("/topic/messages")
  public  ChatMessage leave(ChatMessage message) {
    ChatMessage newMessage = new ChatMessage();
    newMessage.setMessage(message.getMessage());
    newMessage.setMessage(message.getMessage());

    return newMessage;
  }

}
