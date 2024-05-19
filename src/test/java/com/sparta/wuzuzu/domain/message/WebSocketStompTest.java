package com.sparta.wuzuzu.domain.message;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.wuzuzu.domain.message.dto.CreateMessageRequest;
import com.sparta.wuzuzu.domain.message.dto.GetMessageResponse;
import com.sparta.wuzuzu.domain.message.serivce.MessageService;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebSocketStompTest {

    private WebSocketStompClient stompClient;

    private String websocketUrl;

    @BeforeEach
    public void setup() {
        websocketUrl = "ws://localhost:8080/gs-guide-websocket";

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void sendMessage() throws Exception {
        // given
        Long roomId = 1L;
        String token = "your-token";
        CreateMessageRequest request = new CreateMessageRequest();

        GetMessageResponse expectedResponse = new GetMessageResponse();

        // when
        StompSession session = stompClient.connect(websocketUrl, new StompSessionHandlerAdapter() {
            })
            .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/chat-rooms/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return GetMessageResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                GetMessageResponse response = (GetMessageResponse) payload;
                // assert response
            }
        });

        session.send("/chat-rooms/" + roomId + "/messages", request);

        // then
    }
}
