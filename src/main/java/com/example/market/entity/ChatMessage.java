package com.example.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Table(name = "chat_room_message")
@Entity
@Getter
@Setter
public class ChatMessage extends CreatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("채팅메세지 Pk")
    private long chatMessagePk;

    private String chatMsg;

    private String sender;

    @JoinColumn
    @Comment("채팅룸 Pk")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoomPk;


}
