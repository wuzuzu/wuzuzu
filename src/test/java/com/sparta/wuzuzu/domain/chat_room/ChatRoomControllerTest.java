package com.sparta.wuzuzu.domain.chat_room;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.wuzuzu.MockSpringSecurityFilter;
import com.sparta.wuzuzu.domain.chat_room.controller.ChatRoomController;
import com.sparta.wuzuzu.domain.chat_room.dto.CreateChatRoomRequest;
import com.sparta.wuzuzu.domain.chat_room.serivce.ChatRoomService;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.global.config.WebSecurityConfig;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(
    controllers = {ChatRoomController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class ChatRoomControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ChatRoomService chatRoomService;

    User user;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        mockSetup();
    }

    private void mockSetup() {
        // Mock 테스트 유저 생성
        Long userId = 100L;
        String email = "abc123@naver.com";
        String userName = "abc123";
        String password = "abc12345";
        UserRole role = UserRole.USER;
        user = User.builder().email(email).userName(userName).password(password).role(role).build();
        user.setUserId(userId);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    @Test
    @DisplayName("채팅방 조회")
    void getChatRooms() throws Exception {
        // given

        // when&then
        mockMvc.perform(get("/api/v1/chat-rooms")
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("채팅방 생성")
    void createChatRoom() throws Exception {
        // given
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
            .chatRoomName("채팅방")
            .description("채팅방 설명")
            .chatRoomTags(Arrays.asList("태그1", "태그2", "태그3"))
            .build();
        String postInfo = objectMapper.writeValueAsString(request);
        MockMultipartFile chatRoom = new MockMultipartFile("chatRoom", "chatRoom",
            "application/json",
            postInfo.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "test image".getBytes()
        );

        // when&then
        mockMvc.perform(multipart("/api/v1/chat-rooms")
                .file(chatRoom)
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("채팅방 생성 실패")
    void createChatRoom_Fail() throws Exception {
        // given
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
            .chatRoomName("채팅방")
            .description("채팅방 설명")
            .chatRoomTags(Arrays.asList("태그1", "태그2", "태그3"))
            .build();
        String postInfo = objectMapper.writeValueAsString(request);
        MockMultipartFile chatRoom = new MockMultipartFile("chatRoom", "chatRoom",
            "application/json",
            postInfo.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "test image".getBytes()
        );

        given(chatRoomService.createChatRoom(any(CreateChatRoomRequest.class),
            any(MultipartFile.class), any(User.class)))
            .willThrow(new RuntimeException(new IOException()));

        // when&then
        mockMvc.perform(multipart("/api/v1/chat-rooms")
                .file(chatRoom)
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("채팅방 입장")
    void enterChatRoom() throws Exception {
        // given
        long chatRoomId = 100L;

        // when&then
        mockMvc.perform(post("/api/v1/chat-rooms/" + chatRoomId)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("내 채팅방 조회")
    void getMyChatRooms() throws Exception {
        // given

        // when&then
        mockMvc.perform(get("/api/v1/chat-rooms/my-rooms")
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());

        verify(chatRoomService, times(1)).getMyChatRooms(any(User.class));
    }

    @Test
    @DisplayName("키워드로 채팅방 조회")
    void searchChatRoomsByKeyword() throws Exception {
        // given
        String keyword = "채팅방";

        // when&then
        mockMvc.perform(get("/api/v1/chat-rooms/search-keyword?keyword=" + keyword)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());

        verify(chatRoomService, times(1)).searchChatRoomsByKeyword(any(String.class));
    }

    @Test
    @DisplayName("태그로 채팅방 조회")
    void searchChatRoomsByTag() throws Exception {
        // given
        String tag = "태그";

        // when&then
        mockMvc.perform(get("/api/v1/chat-rooms/search-tag?tag=" + tag)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());

        verify(chatRoomService, times(1)).searchChatRoomsByTag(any(String.class));
    }
}
