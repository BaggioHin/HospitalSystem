package com.example.HospitalSystem.dto.response;

import com.example.HospitalSystem.entity.chat.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    private Long id;
    private String content;
    private String createdAt;
    private Long senderId;
    private String senderName;

    public static MessageResponse fromEntity(Message message) {
        String fullname = message.getSender().getFirstName() + " " + message.getSender().getLastName();
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt().toString())
                .senderId(message.getSender().getId())
                .senderName(fullname)
                .build();
    }
}
