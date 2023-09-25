package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.service.BoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserWebController {
    private final BoardService boardService;
    @GetMapping("/user/signup")
    public String signupPage() {
        return "/user/signup";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "/user/login";
    }

    @GetMapping("/user/update")
    public String updatePage() {
        return "/user/update";
    }

    @GetMapping("/user/escape")
    public String escapePage() {
        return "/user/escape";
    }

    @GetMapping("/user/logout")
    public String logout() {
        /**
         * 쿠키 삭제 기능 추가
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "/user/login";
    }
    @GetMapping("/main")

    public String mainPage(Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllUserEnableBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("message", "Thymeleaf를 사용한 Spring 웹서비스");
        return "/main";
    }
    @GetMapping("/user/board")
    public String createBoard(Model model) {
        return "/board/boardCreate";
    }

    @GetMapping("/user/board/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllUserEnableBoard();
        model.addAttribute("boards", boards);
        boards.forEach(System.out::println);
        BoardReadAllResponseDto boardSearchList = boardService.readChoiceBoard(boardId);
        model.addAttribute("boardData", boardSearchList);

        return "/board/boardview";  // boardView.html로 이동
    }

}
