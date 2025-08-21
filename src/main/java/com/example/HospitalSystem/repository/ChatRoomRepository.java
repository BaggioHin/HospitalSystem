package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.constant.RoomType;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // Tìm tất cả room theo type
    List<ChatRoom> findByType(RoomType type);

    // Tìm room theo tên (thường dùng cho group)
    Optional<ChatRoom> findByName(String name);
}