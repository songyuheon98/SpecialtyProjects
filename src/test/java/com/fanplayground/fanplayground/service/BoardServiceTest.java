package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserBoard;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.exception.UserNotFoundException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.BoardRepository;
import com.fanplayground.fanplayground.repository.UserBoardRepository;
import com.fanplayground.fanplayground.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class BoardServiceTest {

    private static MockedStatic<SecurityUtil> mockedSecurityUtil;
    @BeforeAll
    static void setUp() {
        mockedSecurityUtil = mockStatic(SecurityUtil.class);
    }
    @AfterAll
    static void tearDown() {
        mockedSecurityUtil.close();
    }


    @Autowired
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private SecurityUtil securityUtil;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserBoardRepository userBoardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createBoardTest() {
        // given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").userBoards(new ArrayList<>()).build();


        User securityUser = User.builder().id(1L).username("username").password("password").nickName("nickName").build();
        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(new ArrayList<>()).userBoards(new ArrayList<>()).build();

        BoardCreateRequestDto requestDto =
                BoardCreateRequestDto.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(boardRepository.save(any())).thenReturn(board);
        when(userBoardRepository.save(any())).thenReturn(UserBoard.builder().build());

        BoardCreateResponseDto result = boardService.createBoard(requestDto);

        // then
        assertEquals(result.getBoardName(), "boardName");
        assertEquals(result.getBoardColor(), "boardColor");
        assertEquals(result.getBoardInfo(), "boardInfo");
        System.out.println(result);
    }

    @Test
    void readAllBoard() {
        //given
        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").userBoards(new ArrayList<>()).build());

        List<BoardReadAllResponseDto> boardList = new ArrayList<>();
        boardList.add(BoardReadAllResponseDto.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").build());

        //when
        when(boardRepository.findAll()).thenReturn(boards);
        List<BoardReadAllResponseDto> result = boardService.readAllBoard();

        //then
        assertThat(result.get(0).getBoardColumns()).isEqualTo(boardList.get(0).getBoardColumns());
        assertThat(result.get(0).getBoardColor()).isEqualTo(boardList.get(0).getBoardColor());
        assertThat(result.get(0).getBoardInfo()).isEqualTo(boardList.get(0).getBoardInfo());
    }

    @Test
    void readChoiceBoard() {

        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").userBoards(new ArrayList<>()).build();

        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));

        BoardReadAllResponseDto result = boardService.readChoiceBoard(1L);

        assertEquals(result.getBoardName(), "boardName");
        assertEquals(result.getBoardColor(), "boardColor");
        assertEquals(result.getBoardInfo(), "boardInfo");
    }

    @Test
    void readAllUserBoard() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build());


        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();


        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(boardRepository.findAll()).thenReturn(boards);
        List<BoardReadAllResponseDto> result = boardService.readAllUserBoard();

        //then
        assertEquals(result.get(0).getBoardName(), "boardName");
        assertEquals(result.get(0).getBoardColor(), "boardColor");
        assertEquals(result.get(0).getBoardInfo(), "boardInfo");
    }

    @Test
    void readAllUserBoard_실패() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build());


        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();


        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException("로그인한 회원을 찾을 수 없습니다."));
        when(boardRepository.findAll()).thenReturn(boards);

        //then
        assertThrows(UserNotFoundException.class, () -> boardService.readAllUserBoard());
    }

    @Test
    void readAllUserEnableBoard() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build());

        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();
        List<UserBoard> userBoardList1 = new ArrayList<>();
        userBoardList1.add(userBoard);

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(userBoardRepository.findAllByInviteUserId(1L)).thenReturn(userBoardList1);
        when(boardRepository.findAll()).thenReturn(boards);
        List<BoardReadAllResponseDto> result = boardService.readAllUserEnableBoard();

        //then
        assertEquals(result.get(0).getBoardName(), "boardName");
        assertEquals(result.get(0).getBoardColor(), "boardColor");
        assertEquals(result.get(0).getBoardInfo(), "boardInfo");
    }
    @Test
    void readAllUserEnableBoard_실패() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();

        List<Board> boards = new ArrayList<>();

        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();
        List<UserBoard> userBoardList1 = new ArrayList<>();
        userBoardList1.add(userBoard);

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(userBoardRepository.findAllByInviteUserId(1L)).thenReturn(userBoardList1);
        when(boardRepository.findAll()).thenReturn(boards);
        List<BoardReadAllResponseDto> result = boardService.readAllUserEnableBoard();

        //then
        assertEquals(result,new ArrayList<>());
    }
    @Test
    void authority() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder().boardId(1L).userName("userName").build();

        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();
        List<UserBoard> userBoardList1 = new ArrayList<>();
        userBoardList1.add(userBoard);

        // when
        when(userBoardRepository.findAllByInviteUserId(1L)).thenReturn(userBoardList1);
        int result = boardService.authority(findByIdUser,requestDto);

        //then
        assertEquals(result,1);
    }
    @Test
    void authority_실패() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder().boardId(2L).userName("userName").build();

        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();
        List<UserBoard> userBoardList1 = new ArrayList<>();
        userBoardList1.add(userBoard);

        // when
        when(userBoardRepository.findAllByInviteUserId(1L)).thenReturn(userBoardList1);
        int result = boardService.authority(findByIdUser,requestDto);

        //then
        assertEquals(result,0);
    }
    @Test
    void boardInvite() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder().boardId(1L).userName("userName").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.ADMIN)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User inviteUser = User.builder().id(2L).username("username").password("password")
                .nickName("nickName").boards(new ArrayList<>()).userBoards(new ArrayList<>()).build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(userRepository.findByUsername("userName")).thenReturn(Optional.ofNullable(inviteUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userBoardRepository.save(any())).thenReturn(UserBoard.builder().build());

        BoardInviteResponseDto result = boardService.boardInvite(requestDto);

        //then
        assertEquals(result.getMsg(),"관리자 권한으로 초대 되었습니다.");
    }

    @Test
    void boardInvite_User_성공() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder().boardId(1L).userName("userName").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User inviteUser = User.builder().id(2L).username("username").password("password")
                .nickName("nickName").boards(new ArrayList<>()).userBoards(new ArrayList<>()).build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(userRepository.findByUsername("userName")).thenReturn(Optional.ofNullable(inviteUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userBoardRepository.save(any())).thenReturn(UserBoard.builder().build());

        BoardInviteResponseDto result = boardService.boardInvite(requestDto);

        //then
        assertEquals(result.getMsg(),"초대 되었습니다.");
    }
    @Test
    void boardInvite_User_실패() {
        //given
        BoardInviteRequestDto requestDto = BoardInviteRequestDto.builder().boardId(1L).userName("userName").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();

        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        User inviteUser = User.builder().id(2L).username("username").password("password")
                .nickName("nickName").boards(new ArrayList<>()).userBoards(new ArrayList<>()).build();

        UserBoard userBoard = UserBoard.builder().inviteUserId(1L).inviteBoardId(1L).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(userRepository.findByUsername("userName")).thenReturn(Optional.ofNullable(inviteUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userBoardRepository.save(any())).thenReturn(UserBoard.builder().build());

        BoardInviteResponseDto result = boardService.boardInvite(requestDto);

        //then
        assertEquals(result.getMsg(),"당신에게는 권한이 없습니다.");
    }
    @Test
    void boardUpdate() {
        //given
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder().boardName("boardName2").boardColor("boardColor2").boardInfo("boardInfo2").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        BoardInviteResponseDto result = boardService.boardUpdate(1L,requestDto);

        // then
        assertEquals(result.getMsg(),"수정되었습니다.");
    }
    @Test
    void boardUpdate_관리자_수정() {
        //given
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder().boardName("boardName2").boardColor("boardColor2").boardInfo("boardInfo2").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);

        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.ADMIN)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        BoardInviteResponseDto result = boardService.boardUpdate(1L,requestDto);

        // then
        assertEquals(result.getMsg(),"관리자 권한으로 수정되었습니다.");
    }
    @Test
    void boardUpdate_실패() {
        //given
        BoardCreateRequestDto requestDto = BoardCreateRequestDto.builder().boardName("boardName2").boardColor("boardColor2").boardInfo("boardInfo2").build();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();


        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        BoardInviteResponseDto result = boardService.boardUpdate(1L,requestDto);

        // then
        assertEquals(result.getMsg(),"당신에게는 권한이 없습니다.");
    }
    @Test
    void deleteBoard() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);
        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.ADMIN)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(boardRepository.save(any())).thenReturn(board);

        BoardInviteResponseDto result = boardService.deleteBoard(1L);

        //then
        assertEquals(result.getMsg(),"관리자 권한으로 보드가 삭제되었습니다.");

    }

    @Test
    void deleteBoard_User_성공() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();
        userBoardList.add(board);
        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(boardRepository.save(any())).thenReturn(board);

        BoardInviteResponseDto result = boardService.deleteBoard(1L);

        //then
        assertEquals(result.getMsg(),"보드가 삭제되었습니다.");

    }
    @Test
    void deleteBoard_User_실패() {
        //given
        Board board = Board.builder().boardName("boardName").boardColor("boardColor").boardInfo("boardInfo").boardId(1L).userBoards(new ArrayList<>()).build();
        List<Board> userBoardList = new ArrayList<>();

        User securityUser = User.builder().id(1L).username("username").password("password")
                .nickName("nickName").build();
        User findByIdUser = User.builder().id(1L).username("username").password("password").role(UserRoleEnum.USER)
                .nickName("nickName").boards(userBoardList).userBoards(new ArrayList<>()).build();

        // when
        given(securityUtil.getPrincipal()).willReturn(Optional.ofNullable(securityUser));
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(board));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(findByIdUser));
        when(boardRepository.save(any())).thenReturn(board);

        BoardInviteResponseDto result = boardService.deleteBoard(1L);

        //then
        assertEquals(result.getMsg(),"당신에게는 권한이 없습니다.");

    }
}