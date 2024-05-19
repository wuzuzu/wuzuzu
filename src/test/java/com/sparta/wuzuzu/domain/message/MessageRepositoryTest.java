package com.sparta.wuzuzu.domain.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import com.sparta.wuzuzu.domain.message.dto.GetMessageResponse;
import com.sparta.wuzuzu.domain.message.entity.Message;
import com.sparta.wuzuzu.domain.message.repository.MessageRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import com.sparta.wuzuzu.global.config.QueryDSLConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(QueryDSLConfig.class)
public class MessageRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    User user;
    ChatRoom chatRoom;
    List<Message> messages = new ArrayList<>();

    @BeforeEach
    void setUp() {
        user = new User("", "", "유저이름", "", "", "", UserRole.USER, false, 0);
        user.setUserId(1L);
        userRepository.save(user);

        chatRoom = ChatRoom.builder().chatRoomId(100L).build();

        for (int i = 0; i < 10; i++) {
            Message message = new Message(1L + i, "메세지" + i, user.getUserId(),
                chatRoom.getChatRoomId());
            messages.add(message);
        }

        messageRepository.saveAll(messages);
    }

    @Test
    @DisplayName("채팅방 ID로 메세지 찾기")
    void findAllByRoomId() {
        // given
        Long roomId = chatRoom.getChatRoomId();
        List<GetMessageResponse> expectedResponse = messages.stream()
            .map(message -> new GetMessageResponse(user.getUserName(), message)).toList();

        // when
        List<GetMessageResponse> actualResponse = messageRepository.findAllByRoomId(roomId);

        // then
        assertEquals(messages.size(), actualResponse.size());

        for (GetMessageResponse actual : actualResponse) {
            boolean flag = false;
            for (GetMessageResponse expected : expectedResponse) {
                if (actual.getMessageId()
                    .equals(expected.getMessageId()) &&
                    actual.getContent()
                        .equals(expected.getContent()) &&
                    actual.getUserId()
                        .equals(expected.getUserId())) {
                    flag = true;
                    break;
                }
            }

            assertTrue(flag);
        }
    }
}
