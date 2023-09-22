package com.fanplayground.fanplayground.dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardInviteRequestDto {
    private Long boardId;
    private String userName;

}
