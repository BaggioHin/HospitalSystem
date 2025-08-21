package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.repository.MessageRepository;
import com.example.HospitalSystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    @Transactional
    public Message saveMessage(ChatRoom room, Users sender, String content) {
        Message message = Message.builder()
                .chatRoom(room)
                .sender(sender)
                .content(content)
                .build();
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> getMessages(ChatRoom room, Pageable pageable) {
        return messageRepository.findByChatRoomOrderByCreatedAtAsc(room, pageable);
    }

    @Override
    public Message getLatestMessage(ChatRoom room) {
        return messageRepository.findFirstByChatRoomOrderByCreatedAtDesc(room);
    }
}
