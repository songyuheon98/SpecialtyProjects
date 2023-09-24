package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    // @RequestBody 는 Json 형식으로 넘겨주어야한다.
    @PostMapping("/board")
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto)  {
        System.out.println("createBoard called with: " + requestDto);
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/board") // Slice
    public List<BoardReadAllResponseDto> ReadAllBoard(){
        return boardService.readAllBoard();
    }

    // @RequestBody -> Json 기반의 메시지를 사용하는 요청의 경우
    @GetMapping("/board/{boardId}")
    public BoardReadAllResponseDto ReadChoiceBoard(@PathVariable Long boardId) {
        return boardService.readChoiceBoard(boardId);
    }

    @GetMapping("/board/userBoard")
    public List<BoardReadAllResponseDto> readAllUserBoard() {
        return boardService.readAllUserBoard();
    }

    @GetMapping("/board/userEnableBoard")
    public List<BoardReadAllResponseDto> ReadAllUserEnableBoard() {
        return boardService.readAllUserEnableBoard();
    }

    @PostMapping("/board/invite")
    public BoardInviteResponseDto boardInvite(@RequestBody BoardInviteRequestDto requestDto) {
        return boardService.boardInvite(requestDto);
    }
//
    //@PathVariable uri -> id
    @PutMapping("/board/{boardId}")
    public BoardInviteResponseDto boardUpdate(@PathVariable Long boardId, @RequestBody BoardCreateRequestDto requestDto){
        return boardService.boardUpdate(boardId,requestDto);
    }
//

    @DeleteMapping("/board/{boardId}")
    public BoardInviteResponseDto deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }

}

