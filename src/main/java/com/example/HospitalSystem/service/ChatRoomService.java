package com.example.HospitalSystem.service;

import com.example.HospitalSystem.constant.RoomType;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.RoomMember;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import java.util.Optional;

import java.util.List;

public interface ChatRoomService {
    public ChatRoom createRoom(String name, RoomType type, List<Users> members);
    Optional<ChatRoom> getRoomById(Long id);
    public List<ChatRoom> getRoomsByType(RoomType type);
    public List<RoomMember> getMembers(ChatRoom room);
    public List<RoomMember> getRoomsOfUser(Users user);
}
