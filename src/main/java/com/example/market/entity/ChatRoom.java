package com.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Table(name = "chat_room")
@Entity
public class ChatRoom extends UpdateAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatRoomId;

    @JoinColumn
    @ManyToOne
    @Comment("상품 Pk")
    private Product ProductPk;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("채팅유저 1")
    private User userPk;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("채팅유저 2")
    private User userPk1;

    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "chatRoomPk")
    @Comment("메세지 조회")
    private List<ChatMessage> chatMessage;

}
