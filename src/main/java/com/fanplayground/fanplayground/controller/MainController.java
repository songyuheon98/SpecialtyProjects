package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final BoardService boardService;
    @GetMapping("/")
    public String index(Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("message", "Thymeleaf를 사용한 Spring 웹서비스");
        return "main.html";

    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        BoardReadAllResponseDto boardSearchList = boardService.readChoiceBoard(boardId);
        model.addAttribute("boardData", boardSearchList);

        return "/board/boardView";  // boardView.html로 이동
    }

}
