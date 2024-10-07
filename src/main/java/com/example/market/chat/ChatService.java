package com.example.market.chat;

import com.example.market.chat.entity.ChatRoomMsgRepository;
import com.example.market.chat.entity.ChatRoomRepository;
import com.example.market.chat.model.ChatMsgDto;
import com.example.market.entity.ChatRoom;
import com.example.market.entity.ChatMessage;
import com.example.market.entity.User;
import com.example.market.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomMsgRepository chatRoomMsgRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // 채팅방 생성
    public long createChatRoom(User user1, User user2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserPk1(user1);
        chatRoom.setUserPk2(user2);
        chatRoom.setState(1); // 초기 상태를 활성으로 설정

        // 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);

        log.info("Created chat room between user {} and user {}", user1.getUserPk(), user2.getUserPk());
        return chatRoom.getChatRoomId();
    }

    // 특정 유저의 모든 채팅방 조회 (상태가 1인 것만)
    public List<ChatRoom> getChatRoomsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList(); // 유저가 존재하지 않으면 빈 리스트 반환
        }

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUserPk1AndState(user, 1);
        chatRooms.addAll(chatRoomRepository.findAllByUserPk2AndState(user, 1));
        return chatRooms;
    }

    // 특정 유저와의 1:1 채팅방 조회
    public ChatRoom getChatRoomBetweenUsers(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1).orElse(null);
        User user2 = userRepository.findById(userId2).orElse(null);

        if (user1 == null || user2 == null) {
            return null; // 유저가 존재하지 않으면 null 반환
        }
        List<ChatRoom> user1ChatRooms = chatRoomRepository.findAllByUserPk1(user1);
        for (ChatRoom chatRoom : user1ChatRooms) {
            if (chatRoom.getUserPk2().getUserPk().equals(user2.getUserPk()) && chatRoom.getState() == 1) {
                return chatRoom; // 활성 상태인 경우만 반환
            }
        }

        return null; // 해당 채팅방이 존재하지 않으면 null 반환
    }

    // 특정 채팅방의 메시지 조회
    public List<ChatMsgDto> getMessagesByChatRoom(ChatRoom chatRoom) {
        List<ChatMessage> messages = chatRoomMsgRepository.findByChatRoomPk(chatRoom);
        return messages.stream()
                .map(this::convertToChatMsgDto)
                .collect(Collectors.toList());
    }

    // 채팅방 나가기
    public void leaveChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
        if (chatRoom != null) {
            chatRoom.setState(2); // 상태를 2로 변경 (비활성화)
            chatRoomRepository.save(chatRoom); // 변경된 엔티티 저장
        }
    }

    private ChatMsgDto convertToChatMsgDto(ChatMessage chatMsg) {
        return new ChatMsgDto(
                chatMsg.getChatMsg(),
                chatMsg.getChatRoomPk().getChatRoomId(),
                chatMsg.getSender(),
                chatMsg.getCreatedAt()
        );
    }
}
