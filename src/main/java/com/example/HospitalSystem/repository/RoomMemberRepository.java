package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.RoomMember;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    // Tìm tất cả thành viên của một phòng
    List<RoomMember> findByChatRoom(ChatRoom chatRoom);

    // Kiểm tra user có trong room hay không
    Optional<RoomMember> findByChatRoomAndUser(ChatRoom chatRoom, Users user);

    // Lấy tất cả phòng mà user tham gia
    List<RoomMember> findByUser(Users user);
}
