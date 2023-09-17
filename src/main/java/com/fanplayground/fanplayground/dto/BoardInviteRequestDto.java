package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardInviteRequestDto {
    private Long boardId;
    private String userName;
    public BoardInviteRequestDto(Long boardId, String userName) {
        this.boardId = boardId;
        this.userName = userName;
    }

}
