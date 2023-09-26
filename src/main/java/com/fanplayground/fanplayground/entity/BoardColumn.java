package com.fanplayground.fanplayground.entity;

import com.fanplayground.fanplayground.dto.boardColumn.create.BoardColumnRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long columnId;

    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "column_no")
    private Long columnNo;


    /**
     * BoardColumn : Card = 1 : n
     */
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "column_id")
    @JsonBackReference
    private List<Card> cards = new ArrayList<>();


    public BoardColumn(BoardColumnRequestDto columnRequestDto) {
        this.columnName = columnRequestDto.getColumnName();
    }

    public void update(Long columnNo) {
        this.columnNo = columnNo;
    }

    public void updateName(String columnName) {
        this.columnName = columnName;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
