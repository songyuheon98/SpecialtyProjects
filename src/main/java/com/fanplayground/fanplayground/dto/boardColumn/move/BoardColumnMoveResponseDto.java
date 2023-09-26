package com.fanplayground.fanplayground.dto.boardColumn.move;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardColumnMoveResponseDto {
    private List<Long> columnsNos;

}