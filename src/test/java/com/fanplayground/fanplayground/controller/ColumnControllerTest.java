package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.service.BoardColumnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class ColumnControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private BoardColumnService boardColumnService;

    @Test
    @DisplayName("컬럼 생성 확인")
    public void testCreateBoardColumn() throws Exception {
        //given
        MessageDto responseDto = MessageDto.builder()
                .msg("해당 컬럼이 추가되었습니다")
                .build();

        when(boardColumnService.createBoardColumn(any(BoardColumnRequestDto.class))).thenReturn(MessageDto.builder().msg("해당 컬럼이 추가되었습니다").build());
        mockMvc.perform(post("/api/column")
//                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BoardColumnRequestDto.builder().build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("해당 컬럼이 추가되었습니다"));

    }

    @Test
    @DisplayName("컬럼 이동 확인")
    public void testMoveBoardColumn() throws Exception {
        // GIVEN
        List<Long> column_Nos = new ArrayList<>();
        column_Nos.add(1L);
        column_Nos.add(2L);

        List<Long> column_Nos2 = new ArrayList<>();
        column_Nos2.add(2L);
        column_Nos2.add(1L);
        BoardColumnMoveResponseDto responseDto = BoardColumnMoveResponseDto.builder().columnsNos(column_Nos).build();

        BoardColumnMoveRequestDto requestDto = BoardColumnMoveRequestDto.builder().columnsNos(column_Nos2).build();
        // when
        when(boardColumnService.moveBoardColumn(any(Long.class), any(BoardColumnMoveRequestDto.class))).thenReturn(responseDto);

        //THEN
        mockMvc.perform(put("/api/column/" + 1L)
//                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.columnsNos[0]").value(1L))
                .andExpect(jsonPath("$.columnsNos[1]").value(2L))
                .andDo(print());

    }

    //
    @Test
    @DisplayName("컬럼 수정 확인")
    public void testUpdateBoardColumn() throws Exception {
        Board board = new Board();

        BoardColumnUpdateRequestDto requestDto = BoardColumnUpdateRequestDto.builder()
                .columnId(1L)
                .columnName("컬럼을 수정하자")
                .build();

        when(boardColumnService.updateBoardColumn(any(BoardColumnUpdateRequestDto.class)))
                .thenReturn(MessageUpdateDto.builder().msg("해당 컬럼명이 수정되었습니다").columnName("컬럼을 수정하자").build());

        mockMvc.perform(put("/api/column")
//                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("해당 컬럼명이 수정되었습니다"))
                .andExpect(jsonPath("$.columnName").value("컬럼을 수정하자"))
                .andDo(print());

    }



}