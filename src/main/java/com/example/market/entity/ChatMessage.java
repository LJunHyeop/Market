package com.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Table(name = "chat_room_message")
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅메세지 Pk")
    private long chatMessagePk;

    @JoinColumn
    @Comment("채팅룸 Pk")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoomPk;


}
