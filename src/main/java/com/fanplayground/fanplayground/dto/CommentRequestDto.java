package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.Card;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Card cardId;
    private Long commentId;
    private String commentInfo;
}
