package com.fanplayground.fanplayground.entity;


import com.fanplayground.fanplayground.dto.CardRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "card")
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String cardName;

    @Column(nullable = false)
    private String cardInfo;

    @Column(nullable = false)
    private String cardColor;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadLine;

    @Column(nullable = false)
    private Long cardNo;


    public Card(CardRequestDto requestDto, String nickName) {
        this.cardName = requestDto.getCardName();
        this.cardInfo = requestDto.getCardInfo();
        this.cardColor = requestDto.getCardColor();
        this.nickName = nickName;
        this.deadLine = requestDto.getDeadLine();
        this.cardNo = requestDto.getCardNo();
    }

    public void update(CardRequestDto requestDto) {
        this.cardName = requestDto.getCardName();
        this.cardInfo = requestDto.getCardInfo();
        this.cardColor = requestDto.getCardColor();
        this.deadLine = requestDto.getDeadLine();
        this.cardNo = requestDto.getCardNo();
    }
}