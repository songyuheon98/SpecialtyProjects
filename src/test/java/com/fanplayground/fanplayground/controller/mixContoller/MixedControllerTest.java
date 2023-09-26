package com.fanplayground.fanplayground.controller.mixContoller;

import com.fanplayground.fanplayground.dto.board.BoardReadAllResponseDto;
import com.fanplayground.fanplayground.dto.card.CardResponseDto;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.service.BoardColumnService;
import com.fanplayground.fanplayground.service.BoardService;
import com.fanplayground.fanplayground.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class MixedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardColumnService boardColumnService;

    @Test
    void index() throws Exception {
        BoardReadAllResponseDto boardReadAllResponseDto = new BoardReadAllResponseDto();
        List<BoardReadAllResponseDto> boardReadAllResponseDtoList = new ArrayList<>();
        boardReadAllResponseDtoList.add(boardReadAllResponseDto);

        when(boardService.readAllBoard()).thenReturn(boardReadAllResponseDtoList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/login"));
    }

    @Test
    void mainPage() throws Exception {
        BoardReadAllResponseDto boardReadAllResponseDto = new BoardReadAllResponseDto();
        List<BoardReadAllResponseDto> boardReadAllResponseDtoList = new ArrayList<>();
        boardReadAllResponseDtoList.add(boardReadAllResponseDto);

        when(boardService.readAllUserEnableBoard()).thenReturn(boardReadAllResponseDtoList);

        mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("/main"));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/user/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/login"));
    }

    @Test
    void getBoard1() throws Exception {
        BoardReadAllResponseDto boardReadAllResponseDto = new BoardReadAllResponseDto();

        when(boardService.readChoiceBoard(any(Long.class))).thenReturn(boardReadAllResponseDto);

        mockMvc.perform(get("/board/{boardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/boardview"));
    }

    @Test
    void getBoard() throws Exception {
        BoardReadAllResponseDto boardReadAllResponseDto = new BoardReadAllResponseDto();
        List<BoardReadAllResponseDto> boardReadAllResponseDtoList = new ArrayList<>();
        boardReadAllResponseDtoList.add(boardReadAllResponseDto);

        when(boardService.readAllUserEnableBoard()).thenReturn(boardReadAllResponseDtoList);
        when(boardService.readChoiceBoard(any(Long.class))).thenReturn(boardReadAllResponseDto);

        mockMvc.perform(get("/user/board/{boardId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/boardview"));
    }

    @Test
    void getCard() throws Exception {
        CardResponseDto cardResponseDto = new CardResponseDto();

        when(cardService.readCard(any(Long.class))).thenReturn(cardResponseDto);

        mockMvc.perform(get("/user/card/{cardId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/card/cardview"));
    }

    @Test
    void readColumn() throws Exception {
        BoardColumn boardColumn = new BoardColumn();

        when(boardColumnService.findBoardColumn(any(Long.class))).thenReturn(boardColumn);

        mockMvc.perform(get("/user/column/{columnId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/column/columnView"));
    }
}