package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.BoardColumn;
import lombok.Getter;


@Getter
public class BoardColumnResponseDto {

    private Long boardId;
    private String columnmName;


    public BoardColumnResponseDto(BoardColumn boardColumn){
        this.columnmName = boardColumn.getColumnName();
    }




}
