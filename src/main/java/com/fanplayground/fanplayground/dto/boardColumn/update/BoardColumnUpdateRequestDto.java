package com.fanplayground.fanplayground.dto.boardColumn.update;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardColumnUpdateRequestDto {

    private Long columnId;
    private String columnName;


}
