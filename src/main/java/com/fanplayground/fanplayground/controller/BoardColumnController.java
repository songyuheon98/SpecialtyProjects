package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.boardColumn.MessageUpdateDto;
import com.fanplayground.fanplayground.dto.boardColumn.move.BoardColumnMoveRequestDto;
import com.fanplayground.fanplayground.dto.boardColumn.move.BoardColumnMoveResponseDto;
import com.fanplayground.fanplayground.dto.boardColumn.create.BoardColumnRequestDto;
import com.fanplayground.fanplayground.dto.boardColumn.update.BoardColumnUpdateRequestDto;
import com.fanplayground.fanplayground.dto.message.MessageDto;
import com.fanplayground.fanplayground.service.BoardColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/column")
@RequiredArgsConstructor
public class BoardColumnController {
    private final BoardColumnService columnService;

    @PostMapping
    public MessageDto createBoardColumn(@RequestBody BoardColumnRequestDto requestDto)  {
        return columnService.createBoardColumn(requestDto);
    }

    @PutMapping
    public MessageUpdateDto updateBoardColumn(@RequestBody BoardColumnUpdateRequestDto requestDto){
        return columnService.updateBoardColumn(requestDto);
    }

//    @PutMapping("/column")
//    public List<BoardColumnMoveResponseDto> moveBoardColumn(@RequestBody BoardColumnMoveRequestDto boardColumnMoveRequestDto){
//        return columnService.moveBoardColumn(boardColumnMoveRequestDto);
//    }


    @PutMapping("/column/{boardId}")
    public BoardColumnMoveResponseDto moveBoardColumn(@PathVariable Long boardId, @RequestBody BoardColumnMoveRequestDto requestDto){
        return columnService.moveBoardColumn(boardId,requestDto);
    }

    @DeleteMapping("/column/{columnId}")
    public MessageDto deleteBoardColumn(@PathVariable Long columnId){
        return columnService.deleteBoardColumn(columnId);
    }



}
