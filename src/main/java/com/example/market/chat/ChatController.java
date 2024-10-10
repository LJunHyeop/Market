package com.example.market.chat;

import com.example.market.entity.ChatRoom;
import com.example.market.entity.User;
import com.example.market.user.repository.UserRepository;
import com.example.market.chat.model.ChatMsgDto; // DTO 추가
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "채팅방 생성 및 조회 및 나가기 ")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    // 1:1 채팅방 생성
    @PostMapping("/create")
    @Operation(summary = "채팅방 생성")
    public ResponseEntity<Long> createChatRoom(@RequestParam Long userId1, @RequestParam Long userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);

        if (user1 == null || user2 == null) {
            return ResponseEntity.badRequest().body(null); // 사용자 유효성 검사
        }

        long chatRoomId = chatService.createChatRoom(user1, user2);
        return ResponseEntity.ok(chatRoomId); // 생성된 채팅방 ID 반환
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // 특정 유저의 모든 채팅방 조회
    @GetMapping("/rooms")
    @Operation(summary = "특정 유저 전체 채팅방 조회")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@RequestParam Long userId) {
        // 유저 다짜여지면 기본으로 로그인한 유저로 조회
        List<ChatRoom> chatRooms = chatService.getChatRoomsByUserId(userId);
        return ResponseEntity.ok(chatRooms);
    }

    // 두 유저 간의 1:1 채팅방 메시지 조회
    @GetMapping("/room/messages")
    @Operation(summary = "1:1 채팅방 메시지 조회")
    public ResponseEntity<List<ChatMsgDto>> getChatMessages(@RequestParam Long userId1, @RequestParam Long userId2) {
        ChatRoom chatRoom = chatService.getChatRoomBetweenUsers(userId1, userId2);

        if (chatRoom == null) {
            return ResponseEntity.notFound().build();
        }

        List<ChatMsgDto> messages = chatService.getMessagesByChatRoom(chatRoom);
        return ResponseEntity.ok(messages);
    }

    // 채팅방 나가기
    @PostMapping("/leave")
    @Operation(summary = "채팅방 나가기")
    public ResponseEntity<Void> leaveChatRoom(@RequestParam Long chatRoomId) {
        chatService.leaveChatRoom(chatRoomId);
        return ResponseEntity.ok().build();
    }
}
