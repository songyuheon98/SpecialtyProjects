package com.fanplayground.fanplayground.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInviteRequestDto {
    private Long boardId;
    private String userName;
}
