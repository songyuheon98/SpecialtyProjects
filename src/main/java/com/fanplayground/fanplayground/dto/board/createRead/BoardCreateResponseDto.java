package com.fanplayground.fanplayground.dto.board.createRead;

import com.fanplayground.fanplayground.entity.Board;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
