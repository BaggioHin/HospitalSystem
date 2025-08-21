package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.constant.RoomType;
import com.example.HospitalSystem.dto.response.MessageResponse;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.service.ChatRoomService;
import com.example.HospitalSystem.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    // Tạo phòng chat mới
    @PostMapping("/rooms")
    public ChatRoom createRoom(@RequestParam String name,
                               @RequestParam RoomType type,
                               @RequestBody List<Long> memberIds) {
        // TODO: lấy User từ memberIds bằng UserService
        List<Users> members = List.of();
        return chatRoomService.createRoom(name, type, members);
    }

    // Lấy danh sách phòng theo loại
    @GetMapping("/rooms")
    public List<ChatRoom> getRooms(@RequestParam(required = false) RoomType type) {
        if (type != null) return chatRoomService.getRoomsByType(type);
        return chatRoomService.getRoomsByType(RoomType.GROUP);
    }

    // Lấy lịch sử tin nhắn trong 1 phòng
    @GetMapping("/rooms/{roomId}/messages")
    public List<MessageResponse> getMessages(@PathVariable Long roomId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        ChatRoom room = chatRoomService.getRoomById(roomId).orElseThrow();
        Page<Message> messages = messageService.getMessages(room, PageRequest.of(page, size));
        return messages.stream().map(MessageResponse::fromEntity).collect(Collectors.toList());
    }
}
