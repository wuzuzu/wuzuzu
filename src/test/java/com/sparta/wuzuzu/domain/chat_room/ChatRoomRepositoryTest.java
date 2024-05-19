package com.sparta.wuzuzu.domain.chat_room;

import static com.sparta.wuzuzu.domain.chat_room.entity.QChatRoom.chatRoom;
import static com.sparta.wuzuzu.domain.chat_room.entity.QMember.member;
import static com.sparta.wuzuzu.domain.common.image.entity.QImage.image;
import static com.sparta.wuzuzu.domain.user.entity.QUser.*;
import static com.sparta.wuzuzu.domain.user.entity.QUser.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.chat_room.dto.GetChatRoomResponse;
import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import com.sparta.wuzuzu.domain.chat_room.entity.Member;
import com.sparta.wuzuzu.domain.chat_room.repository.ChatRoomRepository;
import com.sparta.wuzuzu.domain.chat_room.repository.MemberRepository;
import com.sparta.wuzuzu.domain.common.image.repository.ImageRepository;
import com.sparta.wuzuzu.domain.user.entity.QUser;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.global.config.QueryDSLConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(QueryDSLConfig.class)
public class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    JPAQueryFactory queryFactory;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(100L);
    }

    @Nested
    @DisplayName("채팅방 생성")
    class CreateChatRoom {

        @Test
        @DisplayName("성공")
        void success() {
            String name = "채팅방";
            String desc = "채팅방 설명";
            String tags = "태그1,태그2,태그3";

            ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(name)
                .description(desc)
                .chatRoomTag(tags)
                .user(user)
                .build();
            ChatRoom created = chatRoomRepository.save(chatRoom);

            assertEquals(chatRoom.getChatRoomName(), created.getChatRoomName());
            assertEquals(chatRoom.getDescription(), created.getDescription());
            assertEquals(chatRoom.getChatRoomTag(), created.getChatRoomTag());
        }

        @Test
        @DisplayName("유저 없음")
        void fail_null_owner() {
            String name = "채팅방";
            String desc = "채팅방 설명";
            String tags = "태그1,태그2,태그3";

            ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(name)
                .description(desc)
                .chatRoomTag(tags)
                .build();
            assertThrows(DataIntegrityViolationException.class, () -> {
                chatRoomRepository.save(chatRoom);
            });
        }

        @Test
        @DisplayName("이름 없음")
        void fail_null_name() {
            String desc = "채팅방 설명";
            String tags = "태그1,태그2,태그3";

            ChatRoom chatRoom = ChatRoom.builder()
                .description(desc)
                .chatRoomTag(tags)
                .user(user)
                .build();
            assertThrows(DataIntegrityViolationException.class, () -> {
                chatRoomRepository.save(chatRoom);
            });
        }
    }

    @Nested
    @DisplayName("채팅방 조회")
    class FindChatRoom {

        List<ChatRoom> chatRooms = new ArrayList<>();

        @BeforeEach
        void setUp() {
            // 100개의 랜덤한 ChatRoom 객체 생성
            for (int i = 0; i < 100; i++) {
                String name = "채팅방" + (i % 10);
                String desc = "채팅방 설명" + (i % 5);
                String tags = "태그" + (i % 3) + ",태그" + ((i + 1) % 3) + ",태그" + ((i + 2) % 3);

                ChatRoom chatRoom = ChatRoom.builder()
                    .chatRoomName(name)
                    .description(desc)
                    .chatRoomTag(tags)
                    .user(user)
                    .build();
                chatRooms.add(chatRoom);
            }

            List<ChatRoom> createdList = chatRoomRepository.saveAll(chatRooms);

            for (ChatRoom chatRoom : createdList) {
                memberRepository.save(
                    Member.builder()
                        .userId(user.getUserId())
                        .chatRoomId(chatRoom.getChatRoomId())
                        .build());
            }
        }

        @Test
        @DisplayName("키워드로 채팅방 찾기")
        void findAllByKeyword() {
            String keyword1 = "방1";
            String keyword2 = "설명2";

            List<ChatRoom> find1 = chatRoomRepository.findAllByKeyword(keyword1);
            List<ChatRoom> find2 = chatRoomRepository.findAllByKeyword(keyword2);

            assertTrue(!find1.isEmpty() && !find2.isEmpty());
            for (ChatRoom chatRoom : find1) {
                assertTrue(
                    chatRoom.getChatRoomName().contains(keyword1) ||
                        chatRoom.getDescription().contains(keyword1));
            }

            for (ChatRoom chatRoom : find2) {
                assertTrue(
                    chatRoom.getChatRoomName().contains(keyword2) ||
                        chatRoom.getDescription().contains(keyword2));
            }
        }

        @Test
        @DisplayName("태그로 채팅방 찾기")
        void findAllByTag() {
            String tag = "태그2";

            List<ChatRoom> find = chatRoomRepository.findAllByTag(tag);

            assertFalse(find.isEmpty());
            for (ChatRoom chatRoom : find) {
                assertTrue(chatRoom.getChatRoomTag().contains(tag));
            }
        }

//        @Test
//        @DisplayName("내 채팅방 찾기")
//        void findAllMyRooms() {
//            System.out.println(user.getUserId());
//            for(Member member : memberRepository.findAll()) {
//                System.out.println(member.getChatRoomId() + " " + member.getUserId());
//            }
//            for(ChatRoom chatRoom : chatRooms) {
//                System.out.println(chatRoom.getChatRoomId() + " " + chatRoom.getUser().getUserId());
//            }
//
//            List<GetChatRoomResponse> find = queryFactory.select(
//                    Projections.constructor(GetChatRoomResponse.class,
//                        QUser.user.userName,
//                        chatRoom.chatRoomId,
//                        chatRoom.chatRoomName,
//                        chatRoom.description,
//                        image.imageUrl,
//                        chatRoom.chatRoomTag))
//                .from(chatRoom)
//                .leftJoin(member).on(member.chatRoomId.eq(chatRoom.chatRoomId))
//                .leftJoin(image).on(image.chatRoom.chatRoomId.eq(chatRoom.chatRoomId))
//                .where(member.userId.eq(user.getUserId()))
//                .fetch();
//
//            assertEquals(chatRooms.size(), find.size());
//        }
    }
}
