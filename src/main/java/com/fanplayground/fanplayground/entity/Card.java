package com.fanplayground.fanplayground.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
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
    private Date deadLine;

    @Column(nullable = false)
    private Long cardNo;

    /**
     *  Card : BoardColumn = n : 1
     */
    @ManyToOne
    @JoinColumn(name = "column_id")
    private BoardColumn boardColumn;


}
