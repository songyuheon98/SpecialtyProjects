package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.entity.UserBoard;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReadAllResponseDto {
    private String boardName;
    private String boardColor;
    private String boardInfo;
    private List<UserBoard> userBoard;
    private List<BoardColumn> boardColumns;

    public BoardReadAllResponseDto(Board board){
        this.boardName = board.getBoardName();
        this.boardColor = board.getBoardColor();
        this.boardInfo = board.getBoardInfo();
        this.userBoard = board.getUserBoards();
        this.boardColumns = board.getBoardColumns();

    }
}
