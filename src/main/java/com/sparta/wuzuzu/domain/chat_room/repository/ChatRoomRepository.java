package com.sparta.wuzuzu.domain.chat_room.repository;

import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRoomRepository {

    @Query("select c from ChatRoom c where c.chatRoomName like concat('%', :keyword, '%') or c.description like concat('%', :keyword, '%')")
    List<ChatRoom> findAllByKeyword(@Param("keyword") String keyword);

    @Query("select c from ChatRoom c where c.chatRoomTag like concat('%', :tag, '%')")
    List<ChatRoom> findAllByTag(@Param("tag") String tag);
}
