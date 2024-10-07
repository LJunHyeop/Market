package com.example.market.chat.entity;


import com.example.market.entity.ChatRoom;
import com.example.market.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByChatRoomId(ChatRoom id);

    List<ChatRoom> findAllByUserPk1(User user);

    List<ChatRoom> findAllByUserPk2(User user);

    List<ChatRoom> findAllByUserPk1AndState(User user, int state);

    List<ChatRoom> findAllByUserPk2AndState(User user, int state);


}
