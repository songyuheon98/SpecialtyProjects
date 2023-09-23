package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.BoardColumnRepository;
import com.fanplayground.fanplayground.repository.BoardRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class BoardColumnServiceTest {

    private static MockedStatic<SecurityUtil> mockedSecurityUtil;
    @BeforeAll
    static void setup() {
        mockedSecurityUtil = mockStatic(SecurityUtil.class);
    }

    @AfterAll
    static void tearDown(){
        mockedSecurityUtil.close();
    }

    @MockBean
    private BoardColumnRepository boardColumnRepository;

    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnService boardColumnService;

    @MockBean
    private SecurityUtil securityUtil;

    @MockBean
    private MockMvc mockMvc;

    @Test
    @DisplayName("컬럼 생성 확인")
    void createBoardColumnTest() {
        //given
        BoardColumnRequestDto requestDto = BoardColumnRequestDto.builder()
                .boardId(1L)
                .columnName("테스트용 컬럼 만들기")
                .build();


        BoardColumn boardColumn = BoardColumn.builder()
                .columnId(1L)
                .columnNo(1L)
                .columnName("테스트용 컬럼 만들기")
                .cards(new ArrayList<>())
                .build();


        Board board = Board.builder()
                .boardId(1L)
                .boardColor("boardColor")
                .boardName("boardName")
                .boardInfo("boardInfo")
                .boardColumns(new ArrayList<>())
                .userBoards(new ArrayList<>())
                .build();


        //when
        when(boardColumnRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(boardColumn));
        when(boardRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(board));
        given(boardColumnRepository.save(any(BoardColumn.class))).willReturn(boardColumn);
        given(boardColumnRepository.findByColumnName(any(String.class))).willReturn(Optional.ofNullable(boardColumn));
//        when(boardColumnRepository.save(any(BoardColumn.class))).thenReturn(BoardColumn);
        //doNothing().when(boardColumnRepository.save(any(BoardColumn.class)));
        //then
        MessageDto result = boardColumnService.createBoardColumn(requestDto);
        assertThat(result.getMsg()).isEqualTo("해당 컬럼이 추가되었습니다");

        verify(boardColumnRepository).save(any(BoardColumn.class));
    }



    @Test
    @DisplayName("컬럼 update 확인")
    void UpdateBoardColumnTest() {
        //given

        BoardColumnUpdateRequestDto requestDto = BoardColumnUpdateRequestDto.builder()
                .columnId(1L)
                .columnName("테스트용 컬럼 만들기")
                .build();


        BoardColumn boardColumn = BoardColumn.builder()
                .columnId(1L)
                .columnNo(1L)
                .columnName("테스트용 컬럼 만들기")
                .cards(new ArrayList<>())
                .build();

        //when
        when(boardColumnRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(boardColumn));
        given(boardColumnRepository.findByColumnName(any(String.class))).willReturn(Optional.ofNullable(boardColumn));
        //then
        MessageUpdateDto result = boardColumnService.updateBoardColumn(requestDto);
        assertThat(result.getMsg()).isEqualTo("해당 컬럼명이 수정되었습니다");

    }

    @Test
    @DisplayName("컬럼 delete 확인")
    void DeleteBoardColumnTest() {
        //given

        BoardColumn boardColumn = BoardColumn.builder()
                .columnId(1L)
                .columnNo(1L)
                .columnName("테스트용 컬럼 만들기")
                .cards(new ArrayList<>())
                .build();

        //when
        when(boardColumnRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(boardColumn));
//        given(boardColumnRepository.findByColumnName(any(String.class))).willReturn(Optional.ofNullable(boardColumn));
        //then
        MessageDto result = boardColumnService.deleteBoardColumn(1L);
        assertThat(result.getMsg()).isEqualTo("해당 컬럼명이 삭제되었습니다");

    }

    @Test
    @DisplayName("컬럼 이동 하기")
    void moveBoardColumnTest() {

        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setColumnName("name");
        boardColumn.setColumnNo(1L);
        boardColumn.setColumnId(1L);

        List<BoardColumn> bb = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            bb.add(boardColumn);
        }

        Board board = new Board();
        board.setBoardId(1L);
        board.setBoardColumns(bb);

        // 준비

        BoardColumnMoveRequestDto requestDto = new BoardColumnMoveRequestDto();
        requestDto.setColumnsNos(Arrays.asList(3L, 2L, 1L)); // 적절한 컬럼 번호로 설정


        when(boardRepository.findById(any(Long.class))).thenReturn(Optional.of(board));

        // 실행
        BoardColumnMoveResponseDto result = boardColumnService.moveBoardColumn(board.getBoardId(), requestDto);
//
//        for (int i= 0; i < result.getColumnsNos().size(); i++){
//            System.out.println(result.getColumnsNos().get(i));
//        }

        assertThat(result.getColumnsNos().get(0)).isEqualTo(1);


        // 확인
//        List<Long> expected = Arrays.asList(3L, 2L, 1L);


        // 데이터베이스에서 보드를 가져와서 확인
//        Board board = boardRepository.findById(boardId).orElse(null);
//        assertNotNull(board);
//        for (int i = 0; i < board.getBoardColumns().size(); i++) {
//            assertEquals(Long.valueOf(expected.get(i)), board.getBoardColumns().get(i).getColumnNo());
//        }
    }
}