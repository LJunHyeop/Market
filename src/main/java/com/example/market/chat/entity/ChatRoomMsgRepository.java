package com.example.market.chat.entity;

import com.example.market.entity.ChatMessage;
import com.example.market.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMsgRepository extends JpaRepository<ChatMessage,Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoomPk= :chatRoom ")
    List<ChatMessage> findByChatRoomOrderBySendTimeAsc(ChatRoom chatRoom);

    Optional<ChatMessage> findById(Long chatRoomPk);

    List<ChatMessage> findByChatRoomPk(ChatRoom chatRoom);
}
