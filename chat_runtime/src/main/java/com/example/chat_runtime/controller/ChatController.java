package com.example.chat_runtime.controller;

import com.example.chat_runtime.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  /*
   * Nhận tin nhắn từ client gửi qua địa chỉ /app/sendMessage.
   * Dữ liệu gửi lên bao gồm: người gửi, người nhận, và nội dung tin nhắn (message).
   * Sau đó, tin nhắn sẽ được phát (broadcast) tới tất cả các client đang subscribe đường dẫn /topic/messages.
   */
  @MessageMapping("/sendMessage")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(ChatMessage message) {
      System.out.println("Nhan tin nhan : " + message);

      return message;
  }


  /*
   * Khi người dùng rời khỏi phòng chat, client sẽ gửi dữ liệu lên địa chỉ /app/leave.
   * Dữ liệu gửi lên bao gồm: người gửi, nội dung tin nhắn và type là "LEAVE".
   * Server sẽ tạo một tin nhắn mới có type là "LEAVE" và phát (broadcast) tới tất cả client
   * đang subscribe đường dẫn /topic/messages để thông báo người này đã rời khỏi phòng.
   */
  @MessageMapping("/leave")
  @SendTo("/topic/messages")
  public ChatMessage leave(String username) {
    ChatMessage newMessage = new ChatMessage();
    newMessage.setContent(username +" đã rời phòng chat");
    /* - HỆ THỐNG BẮN RA THONG BÁO */
    newMessage.setNguoiGui("System");
    newMessage.setType("LEAVE");

    return newMessage;
  }

  /*
   * Khi một người dùng tham gia vào phòng chat, client sẽ gửi dữ liệu lên địa chỉ /app/join.
   * Dữ liệu bao gồm: người gửi, nội dung thông báo và type là "JOIN".
   * Server sẽ tạo một tin nhắn mới có type là "JOIN" và phát (broadcast) tới tất cả client
   * đang subscribe đường dẫn /topic/messages để thông báo rằng người này đã tham gia phòng chat.
   */
  @MessageMapping("/join")
  @SendTo("/topic/messages")
  public ChatMessage join(String username) {
    ChatMessage newMessage = new ChatMessage();
    newMessage.setContent(username + "đã tham gia phong chat");
    newMessage.setNguoiGui(username);
    newMessage.setType("JOIN");
    return newMessage;
  }


}
