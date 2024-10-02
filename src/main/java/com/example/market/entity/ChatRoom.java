package com.example.market.entity;

import jakarta.persistence.*;

@Table(name = "chat_room")
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatRoomId;



}
