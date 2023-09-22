package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.dto.BoardCreateRequestDto;
import com.fanplayground.fanplayground.dto.BoardInviteRequestDto;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test-application.properties")
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value="/sql/BoardRepositoryTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/BoardRepositoryTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoardService boardService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void securityUserTest() {
        User user = new User();
        user.setId(21L);
        user.setNickName("nickname");
        user.setUsername("bin0016");
        user.setPassword("Bin@12345");
        user.setRole(UserRoleEnum.USER);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    void createBoard_보드생성_성공() throws Exception {
        //then
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();

        //when
        //then
        mockMvc.perform(post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardName").value("boardName"))
                .andExpect(jsonPath("$.boardColor").value("boardColor"))
                .andExpect(jsonPath("$.boardInfo").value("boardInfo"));

    }


    @Test
    void readAllBoard() throws Exception {
        mockMvc.perform(get("/api/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardName").value("boardName1"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo"));

    }


    @Test
    void readChoiceBoard() throws Exception {
        mockMvc.perform(get("/api/board/{boardId}", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardName").value("boardName1"))
                .andExpect(jsonPath("$.boardColor").value("boardColor"))
                .andExpect(jsonPath("$.boardInfo").value("boardInfo"));

    }


    @Test
    @Transactional
    void readAllUserBoard() throws Exception {
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        mockMvc.perform(get("/api/board/userBoard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardName").value("boardName2"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor2"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo2"));

    }


    @Test
    void readAllUserEnableBoard() throws Exception {
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        mockMvc.perform(get("/api/board/userBoard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardName").value("boardName2"))
                .andExpect(jsonPath("$[0].boardColor").value("boardColor2"))
                .andExpect(jsonPath("$[0].boardInfo").value("boardInfo2"));

    }


//    @Test
//    @Transactional
//    void boardInvite() throws Exception {
//        //given
//        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
//                .boardId(1L)
//                .userName("bin0016")
//                .build();
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        mockMvc.perform(post("/api/board/invite")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto))
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("초대 되었습니다."));
//
//    }


    @Test
    @Transactional
    void boardInvite_초대실패() throws Exception {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
                .boardId(100L)
                .userName("bin0016")
                .build();
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        mockMvc.perform(post("/api/board/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("당신에게는 권한이 없습니다."));

    }


//    @Test
//    void boardUpdate() throws Exception {
//        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
//                .boardName("boardName23")
//                .boardColor("boardColor23")
//                .boardInfo("boardInfo23")
//                .build();
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        mockMvc.perform(put("/api/board/{boardId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto))
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("수정되었습니다."));
//
//
//    }

    @Test
    void boardUpdate_실패() throws Exception {
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName23")
                .boardColor("boardColor23")
                .boardInfo("boardInfo23")
                .build();
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        mockMvc.perform(put("/api/board/{boardId}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("당신에게는 권한이 없습니다."));


    }

//    @Test
//    @Transactional
//    void deleteBoard() throws Exception {
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        mockMvc.perform(delete("/api/board/{boardId}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("보드가 삭제되었습니다."));
//    }

    @Test
    @Transactional
    void deleteBoard_실패() throws Exception {
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        mockMvc.perform(delete("/api/board/{boardId}", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("당신에게는 권한이 없습니다."));
    }
}