package com.fanplayground.fanplayground.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String nickName;
}
