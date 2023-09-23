package com.fanplayground.fanplayground.dto;

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
