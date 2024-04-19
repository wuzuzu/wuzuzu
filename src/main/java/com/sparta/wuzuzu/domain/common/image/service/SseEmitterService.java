package com.sparta.wuzuzu.domain.common.image.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
public class SseEmitterService {

    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String key, Long timeout) {
        // timeout 설정
        SseEmitter emitter = new SseEmitter(timeout);

        // Timeout 시 연결 종료 이벤트
        emitter.onTimeout(() -> {
            log.info("[" + key + "] SSE 세션 타임 아웃");
            emitterMap.remove(key);
            emitter.complete();
        });

        // 에러 이벤트
        emitter.onError((e) -> {
            log.info("[" + key + "] SSE 세션 오류 발생");
            emitterMap.remove(key);
            emitter.complete();
        });

        // 연결 종료 이벤트
        emitter.onCompletion(() -> {
            if(emitterMap.remove(key) != null) {
                log.info("[" + key + "] SSE 세션 완료");
            }
        });

        emitterMap.put(key, emitter);

        return emitter;
    }

    public <T> void send(String key, T data, String name) throws IOException {
        SseEmitter emitter = emitterMap.get(key);

        if(emitter != null) {
            emitter.send(SseEmitter.event().name(name).data(data).build());
        }
    }

    public void complete(String key) {
        SseEmitter emitter = emitterMap.remove(key);

        if(emitter != null) {
            emitter.complete();
        }
    }
}
