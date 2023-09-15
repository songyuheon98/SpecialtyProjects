package com.fanplayground.fanplayground.dto;


import com.fanplayground.fanplayground.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public ReplyResponseDto(Reply saveReply) {
        this.id = saveReply.getId();
        this.content = saveReply.getContent();
        this.username = saveReply.getUsername();
        this.createdAt = saveReply.getCreatedAt();
        this.modifiedAt = saveReply.getModifiedAt();
    }


}