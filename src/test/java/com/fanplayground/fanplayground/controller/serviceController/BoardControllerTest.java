package com.fanplayground.fanplayground.controller.serviceController;

import com.fanplayground.fanplayground.dto.board.createRead.BoardCreateRequestDto;
import com.fanplayground.fanplayground.dto.board.createRead.BoardCreateResponseDto;
import com.fanplayground.fanplayground.dto.board.inviteUpdateDelete.BoardInviteRequestDto;
import com.fanplayground.fanplayground.dto.board.inviteUpdateDelete.BoardInviteResponseDto;
import com.fanplayground.fanplayground.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class BoardControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private BoardService boardService;

    @Test
    void createBoard() throws Exception {
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();

        when(boardService.createBoard(any(BoardCreateRequestDto.class))).thenReturn(BoardCreateResponseDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build());

        ResultActions resultActions = mockMvc.perform(post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.boardName").value("boardName"))
                .andExpect(jsonPath("$.boardColor").value("boardColor"))
                .andExpect(jsonPath("$.boardInfo").value("boardInfo"));
    }
    @Test
    void boardInvite() throws Exception {
        BoardInviteRequestDto boardInviteRequestDto = BoardInviteRequestDto.builder()
                .boardId(1L)
                .userName("username")
                .build();

        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("초대 되었습니다.")
                .build();

        when(boardService.boardInvite(any(BoardInviteRequestDto.class))).thenReturn(boardInviteResponseDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/board/invite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardInviteRequestDto)))
                .andDo(print())
                .andExpect(jsonPath("$.msg").value("초대 되었습니다."));
    }

    @Test
    void boardUpdate() throws Exception {
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();

        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("초대 되었습니다.")
                .build();

        when(boardService.boardUpdate(any(Long.class),any(BoardCreateRequestDto.class))).thenReturn(boardInviteResponseDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/board/{boardId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andDo(print())
                        .andExpect(jsonPath("$.msg").value("초대 되었습니다."));
    }
    @Test
    void deleteBoard() throws Exception {
        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("삭제 되었습니다.")
                .build();

        when(boardService.deleteBoard(any(Long.class))).thenReturn(boardInviteResponseDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/board/{boardId}",1L))
                .andDo(print())
                .andExpect(jsonPath("$.msg").value("삭제 되었습니다."));

    }
}