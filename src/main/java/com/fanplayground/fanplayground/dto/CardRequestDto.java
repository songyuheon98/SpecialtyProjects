package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CardRequestDto {
    private Long boardId;
    private Long columnId;
    private String cardName;
    private String cardInfo;
    private String cardColor;
    private Date deadLine;
    private Long cardNo;
}
