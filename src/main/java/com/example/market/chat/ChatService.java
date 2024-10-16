package com.example.market.chat;

import com.example.market.chat.entity.ChatRoomMsgRepository;
import com.example.market.chat.entity.ChatRoomRepository;
import com.example.market.chat.model.ChatMsgDto; // DTO 추가
import com.example.market.entity.ChatRoom;
import com.example.market.entity.ChatMessage;
import com.example.market.entity.User;

import com.example.market.product.repository.ProductRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final AuthenticationFacade authenticationFacade;
    private final ProductRepository productRepository;

    // 채팅방 생성
    public long createChatRoom(Long productPk) {
        // 게시글의 유저 ID 조회
        Long userId = productRepository.findUserPkByProductPk(productPk);

        // 유저 ID가 null이면 예외 처리
        if (userId == null) {
            throw new IllegalArgumentException("Invalid product PK or user not found.");
        }

        // 로그인한 유저 정보 가져오기
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());

        // 상대 유저 정보 가져오기
        User productUser = userRepository.findById(userId).orElse(null);
        if (productUser == null) {
            throw new IllegalArgumentException("User not found for the given product.");
        }

        // 기존 채팅방 조회
        ChatRoom existingChatRoom = chatRoomRepository.findByUsers(loginUser, productUser);
        if (existingChatRoom != null) {
            return existingChatRoom.getChatRoomId(); // 기존 채팅방이 있으면 그 ID 반환
        }

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserPk1(loginUser);
        chatRoom.setUserPk2(productUser);
        chatRoom.setState(1); // 초기 상태를 활성으로 설정

        // 채팅방 저장
        chatRoom = chatRoomRepository.save(chatRoom);
        log.info("Created chat room between user {} and user {}", loginUser.getUserPk(), productUser.getUserPk());
        return chatRoom.getChatRoomId();
    }

    // 메시지 저장
    public void saveMessage(ChatMsgDto chatMsgDto) {
        if (chatMsgDto == null) {
            throw new IllegalArgumentException("ChatMsgDto cannot be null.");
        }

        User sender = getCurrentUser(authenticationFacade.getLoginUser());
        if (sender == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // chatRoomPk를 사용하여 채팅룸 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatMsgDto.getChatRoomPk())
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found."));

        // 채팅 메시지 생성
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoomPk(chatRoom);
        chatMessage.setSender(sender.getUserName()); // sender가 User 객체라면, User의 필드에 맞춰서 변경 필요
        chatMessage.setChatMsg(chatMsgDto.getChatMsg());
        chatMessage.setCreatedAt(LocalDateTime.now()); // 메시지 생성 시간 설정

        chatRoomMsgRepository.save(chatMessage);
        log.info("Saved message: {} from user {} in chat room {}", chatMsgDto.getChatMsg(), sender.getUserPk(), chatRoom.getChatRoomId());
    }

    // 특정 유저의 모든 채팅방 조회 (상태가 1인 것만)
    public List<ChatRoom> getChatRoomsByUserId(Long userId) {
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        if (loginUser == null || loginUser.getUserPk().equals(userId)) {
            return Collections.emptyList();
        }

        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms.addAll(chatRoomRepository.findAllByUserPk1AndState(loginUser, 1));
        return chatRooms;
    }

    // 특정 유저와의 1:1 채팅방 조회
    public ChatRoom getChatRoomBetweenUsers(Long otherUserId) {
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        if (loginUser == null) {
            return null;
        }

        User otherUser = userRepository.findById(otherUserId).orElse(null);
        if (otherUser == null || loginUser.getUserPk().equals(otherUser.getUserPk())) {
            return null; // 상대방 유저가 없거나 로그인한 유저와 동일할 경우 null 반환
        }

        List<ChatRoom> loginUserChatRooms = chatRoomRepository.findAllByUserPk1(loginUser);
        for (ChatRoom chatRoom : loginUserChatRooms) {
            if (chatRoom.getUserPk2().getUserPk().equals(otherUser.getUserPk())) {
                return chatRoom; // 채팅방 반환
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
            chatRoomRepository.save(chatRoom);
        }
    }

    // 채팅방 들고올때 메세지 보내는시간등 담는 DTO
    private ChatMsgDto convertToChatMsgDto(ChatMessage chatMsg) {
        return new ChatMsgDto(
                chatMsg.getChatMsg(),
                chatMsg.getChatRoomPk().getChatRoomId(),
                chatMsg.getSender(),
                chatMsg.getCreatedAt()
        );
    }

    // 유저 들고오는는거
    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }

    // 채팅방 조회
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElse(null); // 없으면 null 반환
    }
}
