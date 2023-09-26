package com.fanplayground.fanplayground.dto.etc;

import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.entity.Card;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnResponseDto {
    private Long columnId;

    private String columnName;

    private Long columnNo;
    private List<Card> cards = new ArrayList<>();

    public ColumnResponseDto(BoardColumn boardColumn){
        this.columnName = boardColumn.getColumnName();
        this.columnNo = boardColumn.getColumnNo();
        this.cards = boardColumn.getCards();
        this.columnId = boardColumn.getColumnId();
    }
}
