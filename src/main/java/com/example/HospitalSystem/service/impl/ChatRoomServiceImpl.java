package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.RoomType;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.RoomMember;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.repository.ChatRoomRepository;
import com.example.HospitalSystem.repository.RoomMemberRepository;
import com.example.HospitalSystem.service.ChatRoomService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    RoomMemberRepository roomMemberRepository;

    @Override
    public ChatRoom createRoom(String name, RoomType type, List<Users> members) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .type(type)
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        for (Users user : members) {
            RoomMember roomMember = RoomMember.builder()
                    .chatRoom(chatRoom)
                    .user(user)
                    .build();
            roomMemberRepository.save(roomMember);
        }
        return chatRoom;
    }

    @Override
    public Optional<ChatRoom> getRoomById(Long id) {
        return chatRoomRepository.findById(id);
    }


    @Override
    public List<ChatRoom> getRoomsByType(RoomType type) {
        return chatRoomRepository.findByType(type);
    }

    @Override
    public List<RoomMember> getMembers(ChatRoom room) {
        return roomMemberRepository.findByChatRoom(room);
    }

    @Override
    public List<RoomMember> getRoomsOfUser(Users user) {
        return roomMemberRepository.findByUser(user);
    }
}
