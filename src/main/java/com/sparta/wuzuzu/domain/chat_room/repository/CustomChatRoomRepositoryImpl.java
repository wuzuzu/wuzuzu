package com.sparta.wuzuzu.domain.chat_room.repository;

import static com.sparta.wuzuzu.domain.chat_room.entity.QChatRoom.chatRoom;
import static com.sparta.wuzuzu.domain.chat_room.entity.QMember.member;
import static com.sparta.wuzuzu.domain.common.image.entity.QImage.image;
import static com.sparta.wuzuzu.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.chat_room.dto.GetChatRoomResponse;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository {

    private final JPAQueryFactory queryFactory;

    public CustomChatRoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GetChatRoomResponse> findAllMyRooms(Long userId) {
        JPAQuery<GetChatRoomResponse> query = queryFactory.select(
                Projections.constructor(GetChatRoomResponse.class,
                    user.userName,
                    chatRoom.chatRoomId,
                    chatRoom.chatRoomName,
                    chatRoom.description,
                    image.imageUrl,
                    chatRoom.chatRoomTag))
            .from(chatRoom)
            .leftJoin(member).on(member.chatRoomId.eq(chatRoom.chatRoomId))
            .leftJoin(image).on(image.chatRoom.chatRoomId.eq(chatRoom.chatRoomId))
            .where(member.userId.eq(userId));

        return query.fetch();
    }

    @Override
    public List<GetChatRoomResponse> findAllNotMyRooms(Long userId) {
        JPAQuery<GetChatRoomResponse> query = queryFactory.select(
                Projections.constructor(GetChatRoomResponse.class,
                    user.userName,
                    chatRoom.chatRoomId,
                    chatRoom.chatRoomName,
                    chatRoom.description,
                    image.imageUrl,
                    chatRoom.chatRoomTag))
            .from(chatRoom)
            .leftJoin(member).on(member.chatRoomId.eq(chatRoom.chatRoomId))
            .leftJoin(image).on(image.chatRoom.chatRoomId.eq(chatRoom.chatRoomId))
            .where(member.userId.ne(userId));

        return query.fetch();
    }
}
