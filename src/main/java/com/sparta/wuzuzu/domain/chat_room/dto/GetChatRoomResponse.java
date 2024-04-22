package com.sparta.wuzuzu.domain.chat_room.dto;

import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChatRoomResponse {

    private String userName;
    private Long chatRoomId;
    private String chatRoomName;
    private String description;
    private String coverImage;
    private List<String> chatRoomTags = new ArrayList<>();

    public GetChatRoomResponse(String userName, ChatRoom chatRoom) {
        this.userName = userName;
        chatRoomId = chatRoom.getChatRoomId();
        chatRoomName = chatRoom.getChatRoomName();
        description = chatRoom.getDescription();
        chatRoomTags = Arrays.stream(chatRoom.getChatRoomTag().split(",")).toList();
    }

    public GetChatRoomResponse(String userName, String imageUrl, ChatRoom chatRoom) {
        this(userName, chatRoom);
        coverImage = imageUrl;
    }

    public GetChatRoomResponse(String userName, Long chatRoomId, String chatRoomName,
        String description, String coverImage, String chatRoomTag) {
        this.userName = userName;
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.description = description;
        this.coverImage = coverImage;
        this.chatRoomTags = Arrays.stream(chatRoomTag.split(",")).toList();
    }
}
