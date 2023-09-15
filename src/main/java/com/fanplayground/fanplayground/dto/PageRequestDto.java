package com.fanplayground.fanplayground.dto;

import lombok.Getter;

@Getter
public class PageRequestDto {
    private int page;
    private int size;
    private String sortBy;
    private Boolean isAsc;
}
