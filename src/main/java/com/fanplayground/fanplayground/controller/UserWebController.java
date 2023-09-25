package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.BoardColumnResponseDto;
import com.fanplayground.fanplayground.dto.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.dto.ColumnResponseDto;
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
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserWebController {
    private final BoardColumnService columnService;
    private final BoardService boardService;
    private final BoardColumnRepository boardColumnRepository;

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

    @GetMapping("/card/update")
    public String updateCard(Model model) {
        return "/card/update";
    }
    @GetMapping("/card/delete")
    public String deleteCard(Model model) {
        return "/card/delete";
    }
    @GetMapping("/card/create")
    public String createCard(Model model) {
        return "/card/create";
    }

    @GetMapping("/user/board/update")
    public String updateBoard(Model model) {
        return "/board/boardUpdate";
    }

    @GetMapping("/user/board/delete")
    public String deleteBoard(Model model) {
        return "/board/boardDelete";
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
    @GetMapping("/user/column/{columnId}")
    public String readColumn(@PathVariable Long columnId, Model model) {
        BoardColumn boardColumn = boardColumnRepository.findByColumnId(columnId).orElseThrow(() ->
                new NullPointerException("해당 컬럼은 없음")
        );
        ColumnResponseDto boardColumnResponseDto = new ColumnResponseDto(boardColumn);
        model.addAttribute("columnData", boardColumnResponseDto);
        return "/column/columnView";
    }

    @GetMapping("/user/column/create")
    public String updateColumn(Model model) {
        return "/column/columnCreate";
    }

}
