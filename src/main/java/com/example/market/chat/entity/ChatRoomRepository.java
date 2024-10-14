package com.example.market.chat.entity;


import com.example.market.entity.ChatRoom;
import com.example.market.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByChatRoomId(ChatRoom id);

    List<ChatRoom> findAllByUserPk1(User user);

    List<ChatRoom> findAllByUserPk2(User user);

    List<ChatRoom> findAllByUserPk1AndState(User user, int state);

    List<ChatRoom> findAllByUserPk2AndState(User user, int state);

    @Query("SELECT c FROM ChatRoom c WHERE (c.userPk1 = :user1 AND c.userPk2 = :user2) OR (c.userPk1 = :user2 AND c.userPk2 = :user1)")
    ChatRoom findByUsers(@Param("user1") User user1, @Param("user2") User user2);

}
