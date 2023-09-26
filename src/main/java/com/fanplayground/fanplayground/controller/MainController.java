package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.board.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.dto.card.CardResponseDto;
import com.fanplayground.fanplayground.repository.CardRepository;
import com.fanplayground.fanplayground.service.BoardService;
import com.fanplayground.fanplayground.service.CardService;
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
    private final CardService cardService;
    private final CardRepository cardRepository;


    @GetMapping("/")
    public String index(Model model) {
        List<BoardReadAllResponseDto> boards = boardService.readAllBoard();
        model.addAttribute("boards", boards);
        model.addAttribute("message", "Thymeleaf를 사용한 Spring 웹서비스");
        return "/user/login";

    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        BoardReadAllResponseDto boardSearchList = boardService.readChoiceBoard(boardId);
        model.addAttribute("boardData", boardSearchList);

        return "/board/boardview";  // boardView.html로 이동
    }



    @GetMapping("/user/card/{cardId}")
    public String getCard(@PathVariable Long cardId, Model model) {
        CardResponseDto cardData = cardService.readCard(cardId);
        model.addAttribute("cardData", cardData);
        return "/card/cardview";  //
    }
}
