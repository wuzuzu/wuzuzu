package com.sparta.wuzuzu.domain.message.dto;

import com.sparta.wuzuzu.domain.message.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageResponse {

    private String userName;
    private Long messageId;
    private String content;

    public GetMessageResponse(String userName, Message message) {
        this.userName = userName;
        this.messageId = message.getMessageId();
        this.content = message.getContent();
    }
}
