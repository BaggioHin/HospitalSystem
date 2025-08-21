package com.example.HospitalSystem.dto.request;
import com.example.HospitalSystem.constant.MessageType;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String fileName;
    private String fileType;
    private String timestamp;
}
