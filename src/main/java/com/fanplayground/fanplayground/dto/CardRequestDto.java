package com.fanplayground.fanplayground.dto;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardRequestDto {
    private Long cardId;
    private Long boardId;
    private Long columnId;
    private String cardName;
    private String cardInfo;
    private String cardColor;
    private Date deadLine;
    private Long cardNo;
}
