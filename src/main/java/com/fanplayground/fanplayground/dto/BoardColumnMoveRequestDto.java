package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardColumnMoveRequestDto {

    private Long columnId;
    private List<BoardColumnMoveRequestEtcDto> BoardColumnMoveRequestEtcDtos;


}
