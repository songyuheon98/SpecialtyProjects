package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardColumnMoveResponseDto {
    private List<Long> columnsNos;

    public BoardColumnMoveResponseDto(List<Long> columnsNos) {
        this.columnsNos = columnsNos;
    }
}