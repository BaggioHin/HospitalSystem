package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.constant.RoomType;
import com.example.HospitalSystem.dto.response.MessageResponse;
import com.example.HospitalSystem.entity.chat.ChatRoom;
import com.example.HospitalSystem.entity.chat.Message;
import com.example.HospitalSystem.entity.chat.RoomMember;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.ChatRoomService;
import com.example.HospitalSystem.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Tạo phòng chat mới
    @PostMapping("/rooms")
    public ChatRoom createRoom(@RequestParam String name,
                               @RequestParam RoomType type,
                               @RequestBody List<Long> memberIds) {
        // TODO: lấy User từ userIds bằng UserService
        List<Users> members = List.of();
        return chatRoomService.createRoom(name, type, members);
    }

    // Lấy danh sách phòng theo loại
    @GetMapping("/rooms")
    public List<ChatRoom> getRooms(@RequestParam(required = false) RoomType type) {
        if (type != null) return chatRoomService.getRoomsByType(type);
        return chatRoomService.getRoomsByType(RoomType.GROUP);
    }

    @PostMapping("/rooms/{roomId}/addMember")
    public ChatRoom addMemberToRoom(@PathVariable Long roomId,
                                    @RequestParam Long userId,
                                    Principal principal) {
        // 1. Lấy phòng chat
        ChatRoom room = chatRoomService.getRoomById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        // 2. Lấy user muốn thêm
        Users userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // 3. Kiểm tra quyền (chỉ admin hoặc creator mới được thêm)
        Users currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        if (!room.getCreator().equals(currentUser)) { // giả sử room có field creator
            throw new RuntimeException("Bạn không có quyền thêm thành viên");
        }

        // 4. Tránh trùng lặp
        boolean alreadyMember = room.getMembers().stream()
                .anyMatch(m -> m.getUser().getId().equals(userId));
        if (alreadyMember) {
            throw new RuntimeException("User đã là thành viên phòng");
        }

        // 5. Thêm thành viên
        RoomMember roomMember = new RoomMember();
        roomMember.setUser(userToAdd);
        room.getMembers().add(roomMember);

        // 6. Lưu lại phòng chat
        ChatRoom updatedRoom = chatRoomService.saveRoom(room);

        // 7. Thông báo realtime tới tất cả client trong phòng
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId,
                userToAdd.getUsername() + " đã tham gia phòng"
        );

        return updatedRoom;
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
