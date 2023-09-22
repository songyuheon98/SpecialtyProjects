package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.h2.index.IndexCondition.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {

//    @BeforeEach
//    void securityUserTest() {
//        User user = new User();
//        user.setId(21L);
//        user.setNickName("nickname");
//        user.setUsername("bin0016");
//        user.setPassword("Bin@12345");
//        user.setRole(UserRoleEnum.USER);
//
//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    }

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

        /**
         * Any를 이용해서 모든 객체 허용 -> return 안되는 문제 해결
         */
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
    void readAllBoard() throws Exception {
        // given
        List<BoardReadAllResponseDto> responseDto = Arrays.asList(
                BoardReadAllResponseDto.builder()
                  .boardName("boardName")
                  .boardColor("boardColor")
                  .boardInfo("boardInfo")
                  .build()
        );

        // when
        when(boardService.ReadAllBoard()).thenReturn(responseDto);

        // then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/board"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].boardName").value("boardName"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo"));
    }

    @Test
    void readChoiceBoard() throws Exception {
        // given
        BoardReadAllResponseDto responseDto = BoardReadAllResponseDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();

        // when
        when(boardService.readChoiceBoard(1L)).thenReturn(responseDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/board/{boardId}",1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.boardName").value("boardName"))
                .andExpect(jsonPath("$.boardColor").value("boardColor"))
                .andExpect(jsonPath("$.boardInfo").value("boardInfo"));

        // then
    }

    @Test
    void readAllUserBoard() throws Exception {
        //given
        List<BoardReadAllResponseDto> responseDto = Arrays.asList(
                BoardReadAllResponseDto.builder()
                        .boardName("boardName")
                        .boardColor("boardColor")
                        .boardInfo("boardInfo")
                        .build()
        );

        // when
        when(boardService.readAllUserBoard()).thenReturn(responseDto);

        // then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/board/userBoard"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].boardName").value("boardName"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo"));
    }

    @Test
    void readAllUserEnableBoard() throws Exception {
        //given
        List<BoardReadAllResponseDto> responseDto = Arrays.asList(
                BoardReadAllResponseDto.builder()
                        .boardName("boardName")
                        .boardColor("boardColor")
                        .boardInfo("boardInfo")
                        .build()
        );

        // when
        when(boardService.readAllUserEnableBoard()).thenReturn(responseDto);

        // then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/board/userEnableBoard"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].boardName").value("boardName"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo"));
    }

    @Test
    void boardInvite() throws Exception {
        // given
        BoardInviteRequestDto boardInviteRequestDto = BoardInviteRequestDto.builder()
                .boardId(1L)
                .userName("username")
                .build();

        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("초대 되었습니다.")
                .build();

        // when
        when(boardService.boardInvite(any(BoardInviteRequestDto.class))).thenReturn(boardInviteResponseDto);

        //then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/board/invite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardInviteRequestDto)))
                .andDo(print())
                .andExpect(jsonPath("$.msg").value("초대 되었습니다."));
    }

    @Test
    void boardUpdate() throws Exception {
        // given
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();

        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("초대 되었습니다.")
                .build();

        // when
        when(boardService.boardUpdate(any(Long.class),any(BoardCreateRequestDto.class))).thenReturn(boardInviteResponseDto);


        //then
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/board/{boardId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andDo(print())
                        .andExpect(jsonPath("$.msg").value("초대 되었습니다."));

//        verify(boardService).boardUpdate(any(Long.class), any(BoardCreateRequestDto.class));

    }

    @Test
    void deleteBoard() throws Exception {

        BoardInviteResponseDto boardInviteResponseDto = BoardInviteResponseDto.builder()
                .msg("삭제 되었습니다.")
                .build();

        // when
        when(boardService.deleteBoard(any(Long.class))).thenReturn(boardInviteResponseDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/board/{boardId}",1L))
                .andDo(print())
                .andExpect(jsonPath("$.msg").value("삭제 되었습니다."));

    }
}