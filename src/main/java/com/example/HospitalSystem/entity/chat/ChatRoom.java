package com.example.HospitalSystem.entity.chat;

import com.example.HospitalSystem.constant.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Loại phòng: 1-1 hoặc nhóm
    @Enumerated(EnumType.STRING)
    private RoomType type;

    private String creator;

    private String name; // tên phòng (nếu nhóm), 1-1 thì có thể null

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMember> members;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
}
