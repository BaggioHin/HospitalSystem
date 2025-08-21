package com.example.HospitalSystem.entity.chat;
import com.example.HospitalSystem.constant.MessageStatus;
import com.example.HospitalSystem.constant.MessageType;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Phòng chat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    // Người gửi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Users sender;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type; // TEXT, IMAGE, FILE, EMOJI...

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;
}
