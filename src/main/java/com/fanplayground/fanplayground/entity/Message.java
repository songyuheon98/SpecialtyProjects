package com.fanplayground.fanplayground.entity;

import lombok.Data;

@Data
public class Message {

    private int statusCode;
    private String msg;

    public Message(String msg,int statusCode) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

}
