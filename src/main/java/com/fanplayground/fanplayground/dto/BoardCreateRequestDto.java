package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateRequestDto {
    private String boardName;
    private String boardColor;
    private String boardInfo;

}
