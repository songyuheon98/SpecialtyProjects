package com.fanplayground.fanplayground.dto.board.createRead;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoardCreateRequestDto {
    private String boardName;
    private String boardColor;
    private String boardInfo;

}
