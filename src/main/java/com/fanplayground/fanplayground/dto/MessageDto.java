package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class MessageDto {
    private String msg;

    public MessageDto(String msg) {

        this.msg = msg;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }
}
