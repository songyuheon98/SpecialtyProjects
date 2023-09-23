package com.fanplayground.fanplayground.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardColumnMoveRequestDto {
    private List<Long> columnsNos;
}
