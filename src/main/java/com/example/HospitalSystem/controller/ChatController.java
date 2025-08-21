//package com.example.HospitalSystem.controller;
//
//import com.example.HospitalSystem.dto.request.ChatMessage;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//
//@RestController
//public class ChatController {
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(ChatMessage message) {
//        message.setTimestamp(LocalDateTime.now().toString());
//        return message;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(ChatMessage message) {
//        message.setTimestamp(LocalDateTime.now().toString());
//        return message;
//    }
//}
