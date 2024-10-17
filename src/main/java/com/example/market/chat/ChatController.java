package com.example.market.chat;

import com.example.market.entity.ChatRoom;
import com.example.market.entity.User;
import com.example.market.chat.model.ChatMsgDto; // DTO 추가
import com.example.market.notice.NoticeController;
import com.example.market.product.repository.ProductRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "채팅방 생성 및 조회 및 나가기 ")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ProductRepository productRepository;
    private final NoticeController sseController; // SseController 주입

    // 1:1 채팅방 생성
    @PostMapping("/create")
    @Operation(summary = "채팅방 생성")
    @PreAuthorize("hasRole('ROLE_1')")
    public ResponseEntity<Long> createChatRoom(@RequestParam Long productPk) {
        // 게시글의 유저 ID 조회
        Long userId = productRepository.findUserPkByProductPk(productPk);
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        // 유저 ID가 유효하지 않으면 오류 반환
        if (userId == null) {
            return ResponseEntity.badRequest().body(null); // 게시글에 해당하는 유저가 없음
        }

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 로그인된 유저가 없음
        }

        // 채팅방 생성 또는 기존 채팅방 조회
        long chatRoomId = chatService.createChatRoom(productPk); // productPk를 전달

        return ResponseEntity.ok(chatRoomId); // 생성된 채팅방 ID 반환
    }

    // 특정 유저의 모든 채팅방 조회
    @GetMapping("/rooms")
    @Operation(summary = "특정 유저 전체 채팅방 조회")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@RequestParam Long userId) {
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        List<ChatRoom> chatRooms = chatService.getChatRoomsByUserId(loginUser.getUserPk());
        return ResponseEntity.ok(chatRooms);
    }

    // 두 유저 간의 1:1 채팅방 메시지 조회
    @GetMapping("/room/messages")
    @Operation(summary = "1:1 채팅방 메시지 조회")
    public ResponseEntity<List<ChatMsgDto>> getChatMessages(@RequestParam Long chatRoomId) {
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        if (loginUser == null) {
            return ResponseEntity.notFound().build();
        }

        ChatRoom chatRoom = chatService.getChatRoomById(chatRoomId);
        if (chatRoom == null) {
            return ResponseEntity.notFound().build();
        }

        List<ChatMsgDto> messages = chatService.getMessagesByChatRoom(chatRoom);
        return ResponseEntity.ok(messages);
    }

    // 채팅 메시지 전송
    @PostMapping("/send")
    @Operation(summary = "채팅 메시지 전송")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMsgDto chatMsgDto) {
        User loginUser = getCurrentUser(authenticationFacade.getLoginUser());
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 메시지 저장
        chatService.saveMessage(chatMsgDto);

        // 클라이언트에게 알림 전송을 위한 메시지 생성
        String notificationMessage = String.format("%s: %s", loginUser.getUserName(), chatMsgDto.getChatMsg());

        // SSE를 통해 클라이언트에게 알림 전송
        sseController.notifyChatClients(notificationMessage); // 알림 메시지를 전달

        return ResponseEntity.ok().build();
    }

    // 채팅방 나가기
    @PostMapping("/leave")
    @Operation(summary = "채팅방 나가기")
    public ResponseEntity<Void> leaveChatRoom(@RequestParam Long chatRoomId) {
        chatService.leaveChatRoom(chatRoomId);
        return ResponseEntity.ok().build();
    }

    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }

    //채팅방 조회
    @GetMapping("/room/{otherUserId}")
    public ResponseEntity<ChatRoom> getChatRoomBetweenUsers(@PathVariable Long otherUserId) {
        ChatRoom chatRoom = chatService.getChatRoomBetweenUsers(otherUserId);
        if (chatRoom != null) {
            return ResponseEntity.ok(chatRoom); // 채팅방이 존재하면 200 OK 반환
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 채팅방이 존재하지 않으면 404 NOT FOUND 반환
    }
}
