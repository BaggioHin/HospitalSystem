package com.example.HospitalSystem.service;

import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    public Message saveMessage(ChatRoom room, Users sender, String content);
    public Page<Message> getMessages(ChatRoom room, Pageable pageable);
    public Message getLatestMessage(ChatRoom room);
}
