package com.fanplayground.fanplayground.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardInviteResponseDto {
    private String msg;
    public BoardInviteResponseDto(String msg){
        this.msg = msg;
    }

}
