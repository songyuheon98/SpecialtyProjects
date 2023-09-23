package com.fanplayground.fanplayground.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardColumnRequestDto {

    private Long boardId;
    private String columnName;



}
