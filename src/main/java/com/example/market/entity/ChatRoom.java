package com.example.market.entity;

import jakarta.persistence.*;

@Table(name = "chat_room")
@Entity
public class ChatRoom extends UpdateAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatRoomId;

    @JoinColumn
    @ManyToOne
    private Product ProductPk;

    @JoinColumn
    @ManyToOne
    private User user;

}
