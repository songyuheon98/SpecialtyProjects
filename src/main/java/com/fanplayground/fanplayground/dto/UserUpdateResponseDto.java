package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResponseDto {
    private String nickName;
    private String msg;

    public UserUpdateResponseDto(String nickName, String msg) {
        this.nickName = nickName;
        this.msg = msg;
    }
}
