package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.UserBoard;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardReadAllResponseDto {
    private String boardName;
    private String boardColor;
    private String boardInfo;
    private List<UserBoard> userBoard;
    public BoardReadAllResponseDto(Board board){
        this.boardName = board.getBoardName();
        this.boardColor = board.getBoardColor();
        this.boardInfo = board.getBoardInfo();
        this.userBoard = board.getUserBoards();
    }
}
