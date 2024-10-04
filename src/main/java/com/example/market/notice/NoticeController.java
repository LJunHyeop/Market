package com.example.market.notice;

import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.annotations.Comment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeController {

    @RestController
    public class SseController {

        private final List<SseEmitter> emitters = new ArrayList<>();

        @GetMapping("/sse")
        public SseEmitter handleSse() {
            SseEmitter emitter = new SseEmitter();
            emitters.add(emitter);
            emitter.onCompletion(() -> emitters.remove(emitter));
            emitter.onTimeout(() -> emitters.remove(emitter));
            return emitter;
        }
        //채팅알람
        @PostMapping("/notify/chat")
        @Operation(summary = "채팅 알람")
        public void notifyClients() {
            for (SseEmitter emitter : emitters) {
                try {
                    // 로그인한 유저가 채팅을 보냇습니다.
                    emitter.send("채팅을 보냈습니다.");
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        }

    }
    //약속잡기 알람
    @PostMapping("/notify/promise")
    @Operation(summary = "약속잡기 알람")
    public void notifyClients(@RequestBody SseEmitter emitter) {

    }

}
