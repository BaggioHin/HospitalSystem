package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.SendMessageRequest;
import com.example.HospitalSystem.dto.response.MessageResponse;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.ChatRoomService;
import com.example.HospitalSystem.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    // Gửi tin nhắn vào 1 phòng
    @MessageMapping("/rooms/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable Long roomId, SendMessageRequest request, Principal principal) {
        ChatRoom room = chatRoomService.getRoomById(roomId).orElseThrow();

        Users sender = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));

        Message saved = messageService.saveMessage(room, sender, request.getContent());

        // publish tới tất cả client trong phòng
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, MessageResponse.fromEntity(saved));
    }
}
