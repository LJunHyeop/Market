package com.example.market.chat.model;

import java.time.LocalDateTime;

public class ChatMsgDto {
    private String chatMsg;     // 메시지 내용
    private Long chatRoomPk;    // 채팅룸 PK
    private String sender;       // 발신자
    private LocalDateTime createdAt; // 메시지 생성 시간

    // 생성자
    public ChatMsgDto(String chatMsg, Long chatRoomPk, String sender, LocalDateTime createdAt) {
        this.chatMsg = chatMsg;
        this.chatRoomPk = chatRoomPk;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    // Getter
    public String getChatMsg() {
        return chatMsg;
    }

    public Long getChatRoomPk() {
        return chatRoomPk;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Send time getter
    public LocalDateTime getSendTime() {
        return createdAt;
    }
}
