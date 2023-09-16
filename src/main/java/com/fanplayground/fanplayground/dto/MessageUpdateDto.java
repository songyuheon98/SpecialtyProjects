package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUpdateDto {

    private String msg;
    private String columnName;

    public MessageUpdateDto(String msg, String columnName){
        this.msg = msg;
        this.columnName = columnName;
    }

}
