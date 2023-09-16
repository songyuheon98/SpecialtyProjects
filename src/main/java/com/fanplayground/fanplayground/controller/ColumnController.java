package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.service.BoardColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ColumnController {
    private final BoardColumnService columnService;

    @PostMapping("/column")
    public MessageDto createBoardColumn(@RequestBody BoardColumnRequestDto requestDto)  {
        return columnService.createBoardColumn(requestDto);
    }

    @PutMapping("/column")
    public MessageUpdateDto updateBoardColumn(@RequestBody BoardColumnUpdateRequestDto requestDto){
        return columnService.updateBoardColumn(requestDto);
    }

    @PutMapping("/column")
    public List<BoardColumnMoveResponseDto> moveBoardColumn(@RequestBody BoardColumnMoveRequestDto boardColumnMoveRequestDto){
        return columnService.moveBoardColumn(boardColumnMoveRequestDto);
    }


    @DeleteMapping("/column/{columnId}")
    public MessageDto deleteBoardColumn(@PathVariable Long columnId){
        return columnService.deleteBoardColumn(columnId);
    }




}
