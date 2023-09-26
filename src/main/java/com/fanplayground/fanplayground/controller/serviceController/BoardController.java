package com.fanplayground.fanplayground.controller.serviceController;

import com.fanplayground.fanplayground.dto.board.*;
import com.fanplayground.fanplayground.dto.board.createRead.BoardCreateRequestDto;
import com.fanplayground.fanplayground.dto.board.createRead.BoardCreateResponseDto;
import com.fanplayground.fanplayground.dto.board.inviteUpdateDelete.BoardInviteRequestDto;
import com.fanplayground.fanplayground.dto.board.inviteUpdateDelete.BoardInviteResponseDto;
import com.fanplayground.fanplayground.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto)  {
        System.out.println("createBoard called with: " + requestDto);
        return boardService.createBoard(requestDto);
    }
    @PostMapping("/invite")
    public BoardInviteResponseDto boardInvite(@RequestBody BoardInviteRequestDto requestDto) {
        return boardService.boardInvite(requestDto);
    }

    @PutMapping("/{boardId}")
    public BoardInviteResponseDto boardUpdate(@PathVariable Long boardId, @RequestBody BoardCreateRequestDto requestDto){
        return boardService.boardUpdate(boardId,requestDto);
    }

    @DeleteMapping("/{boardId}")
    public BoardInviteResponseDto deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }

}

