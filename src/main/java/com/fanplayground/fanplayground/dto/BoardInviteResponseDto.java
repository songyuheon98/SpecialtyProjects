package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardInviteResponseDto {
    private String msg;
    public BoardInviteResponseDto(String msg){
        this.msg = msg;
    }
}
