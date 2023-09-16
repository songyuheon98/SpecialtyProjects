package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateResponseDto {
    private String boardName;
    private String boardColor;
    private String boardInfo;

    public BoardCreateResponseDto(Board board) {
        this.boardName = board.getBoardName();
        this.boardColor = board.getBoardColor();
        this.boardInfo = board.getBoardInfo();
    }
}
