package com.sparta.wuzuzu.domain.message;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.wuzuzu.MockSpringSecurityFilter;
import com.sparta.wuzuzu.domain.chat_room.controller.ChatRoomController;
import com.sparta.wuzuzu.domain.message.serivce.MessageService;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.global.config.WebSecurityConfig;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {ChatRoomController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class MessageControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    MessageService messageService;

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
    @DisplayName("채팅방 메세지 조회")
    void getRoomMessages() throws Exception {
        // given
        long roomId = 100L;

        // when&then
        mockMvc.perform(get("/api/v1/chat-rooms/" + roomId + "/messages")
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
            .andDo(print())
            .andExpect(status().isOk());

        verify(messageService, times(1)).getRoomMessages(any(Long.class));
    }
}
