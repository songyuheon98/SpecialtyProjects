package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String msg;

    public MessageDto(String msg) {

        this.msg = msg;
    }

}
