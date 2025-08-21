package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {

    // Lấy danh sách phân trang theo ChatRoom, sắp xếp tăng dần theo createdAt
    Page<Message> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom, Pageable pageable);

    // Lấy message mới nhất trong 1 phòng (hữu ích để hiển thị preview)
    Message findFirstByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);
}