package com.sparta.wuzuzu.domain.chat_room.repository;

import com.sparta.wuzuzu.domain.chat_room.dto.GetChatRoomResponse;
import java.util.List;

public interface CustomChatRoomRepository {

    List<GetChatRoomResponse> findAllMyRooms(Long userId);
    List<GetChatRoomResponse> findAllNotMyRooms(Long userId);
}
