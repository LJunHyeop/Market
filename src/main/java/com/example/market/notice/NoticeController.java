package com.example.market.notice;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notifications") // 기본 경로 설정
public class NoticeController {

    private final List<SseEmitter> chatEmitters = new ArrayList<>(); // 채팅 알림을 위한 리스트
    private final List<SseEmitter> promiseEmitters = new ArrayList<>(); // 약속잡기 알림을 위한 리스트

    @GetMapping("/sse/chat") // 채팅 알림을 위한 SSE 엔드포인트
    public SseEmitter handleChatSse() {
        SseEmitter emitter = new SseEmitter();
        chatEmitters.add(emitter);
        emitter.onCompletion(() -> chatEmitters.remove(emitter));
        emitter.onTimeout(() -> chatEmitters.remove(emitter));
        return emitter;
    }

    @PostMapping("/notify/chat") // 채팅 알람 전송
    @Operation(summary = "채팅 알람")
    public void notifyChatClients() {
        for (SseEmitter emitter : chatEmitters) {
            try {
                emitter.send("채팅을 보냈습니다.");
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }

    @GetMapping("/sse/promise") // 약속잡기 알림을 위한 SSE 엔드포인트
    public SseEmitter handlePromiseSse() {
        SseEmitter emitter = new SseEmitter();
        promiseEmitters.add(emitter);
        emitter.onCompletion(() -> promiseEmitters.remove(emitter));
        emitter.onTimeout(() -> promiseEmitters.remove(emitter));
        return emitter;
    }

    @PostMapping("/notify/promise") // 약속잡기 알람 전송
    @Operation(summary = "약속잡기 알람")
    public void notifyPromiseClients(@RequestBody String message) {
        for (SseEmitter emitter : promiseEmitters) {
            try {
                emitter.send(message); // 클라이언트에게 전달할 메시지
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }
}
