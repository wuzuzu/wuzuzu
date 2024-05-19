package com.sparta.wuzuzu.domain.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.sparta.wuzuzu.domain.chat_room.dto.CreateChatRoomRequest;
import com.sparta.wuzuzu.domain.chat_room.dto.GetChatRoomResponse;
import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import com.sparta.wuzuzu.domain.chat_room.entity.Member;
import com.sparta.wuzuzu.domain.chat_room.repository.ChatRoomRepository;
import com.sparta.wuzuzu.domain.chat_room.repository.MemberRepository;
import com.sparta.wuzuzu.domain.chat_room.serivce.ChatRoomService;
import com.sparta.wuzuzu.domain.common.image.service.ImageService;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    ChatRoomRepository chatRoomRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ImageService imageService;

    ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        chatRoomService = new ChatRoomService(chatRoomRepository, memberRepository, imageService);
    }

    @Nested
    @DisplayName("채팅방 생성")
    class Create {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            User user = new User();
            user.setUserId(100L);

            CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .chatRoomName("채팅방")
                .description("채팅방 설명")
                .chatRoomTags(Arrays.asList("태그1", "태그2"))
                .build();
            ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(100L)
                .chatRoomName("채팅방")
                .description("채팅방 설명")
                .chatRoomTag("태그1,태그2")
                .user(user)
                .build();
            given(chatRoomRepository.save(any(ChatRoom.class)))
                .willReturn(chatRoom);

            Member member = Member.builder().memberId(100L).chatRoomId(100L).memberId(100L).build();
            given(memberRepository.save(any(Member.class))).willReturn(member);

            // when
            GetChatRoomResponse response = chatRoomService.createChatRoom(request, null, user);

            // then
            verify(memberRepository, times(1)).save(any(Member.class));
            verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));

            assertEquals(response.getChatRoomName(), chatRoom.getChatRoomName());
            assertEquals(response.getDescription(), chatRoom.getDescription());
            assertEquals(response.getChatRoomTags(), request.getChatRoomTags());
        }

        @Test
        @DisplayName("이미지 업로드 실패")
        void fail_image() throws IOException {
            // given
            User user = new User();
            user.setUserId(100L);

            CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .chatRoomName("채팅방")
                .description("채팅방 설명")
                .chatRoomTags(Arrays.asList("태그1", "태그2"))
                .build();
            ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(100L)
                .chatRoomName("채팅방")
                .description("채팅방 설명")
                .chatRoomTag("태그1,태그2")
                .user(user)
                .build();
            given(chatRoomRepository.save(any(ChatRoom.class)))
                .willReturn(chatRoom);

            Member member = Member.builder().memberId(100L).chatRoomId(100L).memberId(100L).build();
            given(memberRepository.save(any(Member.class))).willReturn(member);

            MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes()
            );
            given(imageService.createImage(image, chatRoom)).willThrow(new IOException());

            // when & then
            assertThrows(RuntimeException.class, () -> {
                chatRoomService.createChatRoom(request, image, user);
            });
        }
    }

    @Test
    @DisplayName("채팅방 입장")
    void enterChatRoom() {
        // given
        Long chatRoomId = 100L;
        User user = new User();
        user.setUserId(100L);
        Member member = Member.builder().memberId(100L).chatRoomId(100L).userId(100L).build();
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        chatRoomService.enterChatRoom(chatRoomId, user);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Nested
    @DisplayName("채팅방 조회")
    class Find {

        @Test
        @DisplayName("내 채팅방 조회")
        void getMyChatRooms() {
            // given
            User user = new User("", "", "유저이름", "", "", "", UserRole.USER, false, 0);
            user.setUserId(100L);

            List<GetChatRoomResponse> expectedResponseList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                expectedResponseList.add(GetChatRoomResponse.builder().userName("유저이름").build());
            }

            given(chatRoomRepository.findAllMyRooms(100L)).willReturn(expectedResponseList);

            // when
            List<GetChatRoomResponse> actualResponseList = chatRoomService.getMyChatRooms(user);

            // then
            verify(chatRoomRepository, times(1)).findAllMyRooms(any(Long.class));
            assertEquals(expectedResponseList.size(), actualResponseList.size());

            for (GetChatRoomResponse response : actualResponseList) {
                assertEquals(response.getUserName(), "유저이름");
            }
        }

        @Test
        @DisplayName("다른 채팅방 조회")
        void getChatRooms() {
            // given
            User user = new User("", "", "유저이름", "", "", "", UserRole.USER, false, 0);
            user.setUserId(100L);

            List<GetChatRoomResponse> expectedResponseList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                expectedResponseList.add(GetChatRoomResponse.builder().userName("유저이름2").build());
            }

            given(chatRoomRepository.findAllNotMyRooms(100L)).willReturn(expectedResponseList);

            // when
            List<GetChatRoomResponse> actualResponseList = chatRoomService.getChatRooms(user);

            // then
            verify(chatRoomRepository, times(1)).findAllNotMyRooms(any(Long.class));
            assertEquals(expectedResponseList.size(), actualResponseList.size());

            for (GetChatRoomResponse response : actualResponseList) {
                assertNotEquals(response.getUserName(), "유저이름");
            }
        }

        @Test
        @DisplayName("키워드 조회")
        void searchChatRoomsByKeyword() {
            // given
            User user = new User("", "", "유저이름", "", "", "", UserRole.USER, false, 0);
            user.setUserId(100L);

            List<ChatRoom> chatRooms = new ArrayList<>();
            List<GetChatRoomResponse> expectedResponseList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                String name = "채팅방" + (i % 10);
                String desc = "채팅방 설명" + (i % 5);
                String tags = "태그" + (i % 3) + ",태그" + ((i + 1) % 3) + ",태그" + ((i + 2) % 3);
                List<String> tags1 = Arrays.asList("태그" + (i % 3), "태그" + ((i + 1) % 3),
                    "태그" + ((i + 2) % 3));

                GetChatRoomResponse response = GetChatRoomResponse.builder()
                    .chatRoomId(100L + i)
                    .chatRoomName(name)
                    .description(desc)
                    .chatRoomTags(tags1)
                    .userName("유저이름")
                    .coverImage(null)
                    .build();
                expectedResponseList.add(response);

                ChatRoom chatRoom = ChatRoom.builder()
                    .chatRoomName(name)
                    .description(desc)
                    .chatRoomTag(tags)
                    .user(user)
                    .build();
                chatRooms.add(chatRoom);
            }

            given(chatRoomRepository.findAllByKeyword(any(String.class))).willReturn(chatRooms);

            // when
            List<GetChatRoomResponse> actualResponseList = chatRoomService.searchChatRoomsByKeyword(
                "채팅방");

            // then
            verify(chatRoomRepository, times(1)).findAllByKeyword(any(String.class));
            for (GetChatRoomResponse response : actualResponseList) {
                assertTrue(response.getChatRoomName().contains("채팅방") || response.getDescription()
                    .contains("채팅방"));
            }
        }

        @Test
        @DisplayName("태그로 조회")
        void searchChatRoomsByTag() {
            // given
            User user = new User("", "", "유저이름", "", "", "", UserRole.USER, false, 0);
            user.setUserId(100L);

            List<ChatRoom> chatRooms = new ArrayList<>();
            List<GetChatRoomResponse> expectedResponseList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                String name = "채팅방" + (i % 10);
                String desc = "채팅방 설명" + (i % 5);
                String tags = "태그" + (i % 3) + ",태그" + ((i + 1) % 3) + ",태그" + ((i + 2) % 3);
                List<String> tags1 = Arrays.asList("태그" + (i % 3), "태그" + ((i + 1) % 3),
                    "태그" + ((i + 2) % 3));

                GetChatRoomResponse response = GetChatRoomResponse.builder()
                    .chatRoomId(100L + i)
                    .chatRoomName(name)
                    .description(desc)
                    .chatRoomTags(tags1)
                    .userName("유저이름")
                    .coverImage(null)
                    .build();
                expectedResponseList.add(response);

                ChatRoom chatRoom = ChatRoom.builder()
                    .chatRoomName(name)
                    .description(desc)
                    .chatRoomTag(tags)
                    .user(user)
                    .build();
                chatRooms.add(chatRoom);
            }

            given(chatRoomRepository.findAllByTag(any(String.class))).willReturn(chatRooms);

            // when
            List<GetChatRoomResponse> actualResponseList = chatRoomService.searchChatRoomsByTag(
                "태그");

            // then
            verify(chatRoomRepository, times(1)).findAllByTag(any(String.class));
            for (GetChatRoomResponse response : actualResponseList) {
                boolean flag = false;
                for(String tag : response.getChatRoomTags()) {
                    if (tag.contains("태그")) {
                        flag = true;
                        break;
                    }
                }

                assertTrue(flag);
            }
        }
    }
}
