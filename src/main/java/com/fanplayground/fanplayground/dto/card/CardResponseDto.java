package com.fanplayground.fanplayground.dto.card;

import com.fanplayground.fanplayground.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardResponseDto {

    private Long cardId;
    private String cardName;
    private String cardInfo;
    private String cardColor;
    private String nickName;
    private Date deadLine;
    private Long cardNo;

    public CardResponseDto(Card card){
        this.cardId = card.getCardId();
        this.cardName = card.getCardName();
        this.cardInfo = card.getCardInfo();
        this.cardColor = card.getCardColor();
        this.nickName = card.getNickName();
        this.deadLine = card.getDeadLine();
        this.cardNo = card.getCardNo();
    }
}

