package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.board.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.dto.etc.ColumnResponseDto;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.repository.BoardColumnRepository;
import com.fanplayground.fanplayground.service.BoardColumnService;
import com.fanplayground.fanplayground.service.BoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MixedController {

    private final BoardService boardService;
    private final BoardColumnService boardColumnService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllUserEnableBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("message", "Thymeleaf를 사용한 Spring 웹서비스");
        return "/main";
    }

    @GetMapping("/user/board/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllUserEnableBoard();
        BoardReadAllResponseDto boardSearchList = boardService.readChoiceBoard(boardId);

        model.addAttribute("boards", boards);
        model.addAttribute("boardData", boardSearchList);

        return "/board/boardview";
    }

    @GetMapping("/user/column/{columnId}")
    public String readColumn(@PathVariable Long columnId, Model model) {
        BoardColumn boardColumn = boardColumnService.findBoardColumn(columnId);
        ColumnResponseDto boardColumnResponseDto = new ColumnResponseDto(boardColumn);
        model.addAttribute("columnData", boardColumnResponseDto);
        return "/column/columnView";
    }

    @GetMapping("/user/logout")
    public String logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "/user/login";
    }
}
