package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.BoardCreateRequestDto;
import com.fanplayground.fanplayground.dto.BoardCreateResponseDto;
import com.fanplayground.fanplayground.dto.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.dto.PostResponseDto;
import com.fanplayground.fanplayground.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

//    // 게시글을 폴더명으로 조회
//    @GetMapping("/folder/{id}")
//    public List<PostResponseDto> getFolder(@PathVariable Long id){
//        return postService.getFolder(id);
//
//    }


    // @RequestBody 는 Json 형식으로 넘겨주어야한다.
    @PostMapping("/board")
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto)  {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/board") // Slice
    public List<BoardReadAllResponseDto> ReadAllBoard(){
        return boardService.ReadAllBoard();
    }

//
    // @RequestBody -> Json 기반의 메시지를 사용하는 요청의 경우
    @GetMapping("/board/{boardId}")
    public BoardReadAllResponseDto ReadChoiceBoard(@PathVariable Long boardId) {
        return boardService.ReadChoiceBoard(boardId);
    }

    @GetMapping("/board/userBoard")
    public List<BoardReadAllResponseDto> ReadAllUserBoard() {
        return boardService.readAllUserBoard();
    }
//
//    //@PathVariable uri -> id
//    @PutMapping("/post/{id}")
//    public ResponseEntity<?> updatePost(@PathVariable Long id,@RequestBody PostRequestDto requestDto,
//                                            @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
//        return postService.updatePost(id, requestDto, tokenValue);
//    }
//
//    @DeleteMapping("/post/{id}")
//    public ResponseEntity<Message> deletePost(@PathVariable Long id, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue){
//        return postService.deletePost(id,  tokenValue);
//    }

}