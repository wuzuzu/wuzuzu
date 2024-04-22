package com.sparta.wuzuzu.domain.chat_room.serivce;

import com.sparta.wuzuzu.domain.chat_room.dto.CreateChatRoomRequest;
import com.sparta.wuzuzu.domain.chat_room.dto.GetChatRoomResponse;
import com.sparta.wuzuzu.domain.chat_room.entity.ChatRoom;
import com.sparta.wuzuzu.domain.chat_room.entity.Member;
import com.sparta.wuzuzu.domain.chat_room.repository.ChatRoomRepository;
import com.sparta.wuzuzu.domain.chat_room.repository.MemberRepository;
import com.sparta.wuzuzu.domain.common.image.service.ImageService;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    public GetChatRoomResponse createChatRoom(CreateChatRoomRequest request, MultipartFile image,
        User user) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        if (request.getChatRoomTags() != null && !request.getChatRoomTags().isEmpty()) {
            for (i = 0; i < request.getChatRoomTags().size() - 1; i++) {
                sb.append(request.getChatRoomTags().get(i)).append(",");
            }

            sb.append(request.getChatRoomTags().get(i));
        }

        ChatRoom chatRoom = chatRoomRepository.save(
            ChatRoom.builder()
                .chatRoomName(request.getChatRoomName())
                .description(request.getDescription())
                .chatRoomTag(sb.toString())
                .user(user)
                .build()
        );

        memberRepository.save(
            Member.builder().chatRoomId(chatRoom.getChatRoomId()).userId(user.getUserId()).build());

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = imageService.createImage(image, chatRoom);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new GetChatRoomResponse(user.getUserName(), imageUrl, chatRoom);
    }

    public Long enterChatRoom(Long chatRoomId, User user) {
        Member member = memberRepository.save(
            Member.builder().chatRoomId(chatRoomId).userId(user.getUserId()).build());

        return member.getMemberId();
    }

    public List<GetChatRoomResponse> getChatRooms(User user) {
        return chatRoomRepository.findAllNotMyRooms(user.getUserId());
    }

    public List<GetChatRoomResponse> getMyChatRooms(User user) {
        return chatRoomRepository.findAllMyRooms(user.getUserId());
    }

    public List<GetChatRoomResponse> searchChatRoomsByKeyword(String keyword) {
        return chatRoomRepository.findAllByKeyword(keyword).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }

    public List<GetChatRoomResponse> searchChatRoomsByTag(String tag) {
        return chatRoomRepository.findAllByTag(tag).stream()
            .map(chatRoom -> new GetChatRoomResponse(chatRoom.getUser().getUserName(), chatRoom))
            .toList();
    }
}
