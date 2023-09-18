package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardColumnMoveRequestDto {
    private List<Integer> columnsNos;
}
