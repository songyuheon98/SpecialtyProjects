package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.exception.UserNotFoundException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.UserRepository;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value="/sql/BoardRepositoryTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/BoardRepositoryTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
//@Sql(value="/sql/BoardRepositoryTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

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
    void createBoard() {
        // given
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build();

        // when
        BoardCreateResponseDto result = boardService.createBoard(requestDto);

        // then
        assertThat(result.getBoardName()).isEqualTo("boardName2");
        assertThat(result.getBoardColor()).isEqualTo("boardColor2");
        assertThat(result.getBoardInfo()).isEqualTo("boardInfo2");

    }
    @Test
    void readAllBoard() {
        // given
        // when
        List<BoardReadAllResponseDto> result = boardService.readAllBoard();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void readChoiceBoard_보드가_존재하는_경우() {
        // given
        Long boardId = 100L;

        // when
         BoardReadAllResponseDto result = boardService.readChoiceBoard(boardId);

        // then
        assertThat(result.getBoardName()).isEqualTo("boardName1");
        assertThat(result.getBoardColor()).isEqualTo("boardColor");
        assertThat(result.getBoardInfo()).isEqualTo("boardInfo");
    }
    @Test
    void readChoiceBoard_보드가_존재하지_않는_경우() {
        // given
        Long boardId = 101L;

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> boardService.readChoiceBoard(boardId));
    }

    @Test
    void readAllUserBoard_로그인_안하고_조회한_경우() {
        // given
        SecurityContextHolder.clearContext();
        // when
        // then
        assertThrows(NoSuchElementException.class, () -> boardService.readAllUserBoard());
    }

    @Test
    /**
     * Sql을 오버라이드 해서 board를 추가하는 SQL실행을 막는다.
     */
    @Sql(value="/sql/userServiceTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void readAllUserBoard_로그인_하고_조회하였지만_결과가_없는_경우() {
        // given
        // when
        List<BoardReadAllResponseDto> result = boardService.readAllUserBoard();

        // then
        assertThat(result).isEqualTo(new ArrayList<>());
    }


//    @Test
//    @Transactional
//    void readAllUserBoard_로그인_하고_조회하였지만_결과가_있는_경우() {
//        // given
//        // when
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//        List<BoardReadAllResponseDto> result = boardService.readAllUserBoard();
//
//
//        // then
//        assertThat(result.size()).isEqualTo(1);
//    }


    @Test
    void 로그인한_유저가_존재하는지_체크(){
        // given
        // when
        User result = boardService.LoginUserExistCheck();
        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 로그인_토큰_안_가지고_있고_로그인한_유저가_존재하는지_유저_DB에_없을때(){
        // given
        SecurityContextHolder.clearContext();
        // when
        // then
        assertThrows(NoSuchElementException.class,()->boardService.LoginUserExistCheck());
    }

    @Test
    @Sql(value="/sql/select.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void 로그인_토큰_가지고_있고_로그인한_유저가_존재하는지_유저_DB에_없을때(){
        // given
        // when
        // then
        assertThrows(UserNotFoundException.class,()->boardService.LoginUserExistCheck());
    }
    @Test
    void readAllUserEnableBoard() {

    }

    @Test
    void readAllUserEnableBoard_로그인_하고_조회하였지만_결과가_없는_경우() {
        // given
        // when
        List<BoardReadAllResponseDto> result = boardService.readAllUserEnableBoard();

        // then
        assertThat(result).isEqualTo(new ArrayList<>());
    }

    @Test
    @Transactional
    @Sql(value="/sql/select.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void readAllUserEnableBoard_로그인_하고_조회하였지만_결과가_있는_경우() {
        // given
        // when
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());
        List<BoardReadAllResponseDto> result = boardService.readAllUserBoard();


        // then
        assertThat(result.size()).isEqualTo(1);
    }

//    @Test
//    @Transactional
//    void authority_요청한_보드에_대한_권한이_있을때() {
//        //given
//        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
//                .boardId(1L)
//                .userName("bin0016")
//                .build();
//
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        // when
//        int result =boardService.authority(userRepository.findByUsername("bin0016").orElse(null),requestDto);
//        // then
//        assertThat(result).isEqualTo(1);
//    }
    @Test
    @Transactional
    void authority_요청한_보드에_대한_권한이_없을때() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
                .boardId(2L)
                .userName("bin0016")
                .build();

        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        // when
        int result =boardService.authority(userRepository.findByUsername("bin0016").orElse(null),requestDto);
        // then
        assertThat(result).isEqualTo(0);
    }
//    @Test
//    @Transactional
//    void boardInvite_권한이_있는_사람_User_이_다른_사람을_보드에_초대할_때() {
//        //given
//        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
//                .boardId(1L)
//                .userName("bin0016")
//                .build();
//
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        // when
//        BoardInviteResponseDto result = boardService.boardInvite(requestDto);
//
//        // then
//        assertThat(result.getMsg()).isEqualTo("초대 되었습니다.");
//
//    }
    @Test
    @Transactional
    @Sql(value="/sql/otherPeople.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void boardInvite_권한이_없는_사람_User_이_다른_사람을_보드에_초대할_때() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
                .boardId(100L)
                .userName("bin0026")
                .build();

        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());

        // when
        BoardInviteResponseDto result = boardService.boardInvite(requestDto);

        // then
        assertThat(result.getMsg()).isEqualTo("당신에게는 권한이 없습니다.");

    }


//    @Test
//    @Transactional
//    @Sql(value="/sql/admin.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void boardInvite_권한이_있는_사람_ADMIN_이_다른_사람을_보드에_초대할_때() {
//        //given
//        SecurityContextHolder.clearContext();
//        User user = new User();
//        user.setId(21L);
//        user.setNickName("nickname");
//        user.setUsername("bin0016");
//        user.setPassword("Bin@12345");
//        user.setRole(UserRoleEnum.ADMIN);
//
//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder()
//                .boardId(1L)
//                .userName("bin0016")
//                .build();
//
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//        // when
//        BoardInviteResponseDto result = boardService.boardInvite(requestDto);
//
//        // then
//        assertThat(result.getMsg()).isEqualTo("관리자 권한으로 초대 되었습니다.");
//
//    }

    @Test
    @Transactional
    void boardUpdate() {
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());


        BoardInviteResponseDto result =
                boardService.boardUpdate(1L,
                    BoardCreateRequestDto.builder()
                        .boardName("boardName2")
                        .boardColor("boardColor2")
                        .boardInfo("boardInfo2")
                        .build());
        assertThat(result.getMsg()).isEqualTo("수정되었습니다.");

    }

//    @Test
//    @Transactional
//    void deleteBoard() {
//        boardService.createBoard(BoardCreateRequestDto.builder()
//                .boardName("boardName2")
//                .boardColor("boardColor2")
//                .boardInfo("boardInfo2")
//                .build());
//
//
//        BoardInviteResponseDto result =
//                boardService.deleteBoard(1L);
//        assertThat(result.getMsg()).isEqualTo("보드가 삭제되었습니다.");
//
//    }


    @Test
    @Transactional
    void deleteBoard2() {
        boardService.createBoard(BoardCreateRequestDto.builder()
                .boardName("boardName2")
                .boardColor("boardColor2")
                .boardInfo("boardInfo2")
                .build());


        BoardInviteResponseDto result =
                boardService.deleteBoard(100L);
        assertThat(result.getMsg()).isEqualTo("당신에게는 권한이 없습니다.");

    }
}