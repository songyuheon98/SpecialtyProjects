package com.fanplayground.fanplayground.dto.boardColumn;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateDto {

    private String msg;
    private String columnName;



}
