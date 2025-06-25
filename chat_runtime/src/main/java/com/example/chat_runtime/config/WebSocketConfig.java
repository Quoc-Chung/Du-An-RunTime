package com.example.chat_runtime.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
   @Override
   public void configureMessageBroker (MessageBrokerRegistry config) {
     /*- KHI MÌNH GỬI TIN NHẮN HOẶC THÔNG BÁO ĐẾN NHÓM THÌ PHẢI CÓ ĐƯỜNG DẪN /topic */
     config.enableSimpleBroker("/topic");
     /*- NÓ CŨNG GIỐNG NHƯ KHAI BÁO -*/
     config.setApplicationDestinationPrefixes("/app");
   }

   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry){

     /*- TÍ MÌNH ĐĂNG KÍ SERVER SOCKET CÁI THÌ MÌNH DÙNG CÁI PATH NÀY */
     registry.addEndpoint("/chat_runtime")
         .setAllowedOriginPatterns("*") /* CHO PHÉP TRUY CẬP CORF TỪ FRONT END */
         .withSockJS(); /* - CẤU HÌNH THÊM ĐỂ HỖ TRỢ WEBSOCKET CHO MỘT SỐ TRÌNH DUYỆT, WEB ẨN DANH KHÔNG HỖ TRỢ WEB SOCKET */
   }
}
