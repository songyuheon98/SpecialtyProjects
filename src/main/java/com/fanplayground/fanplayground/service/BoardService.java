package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserBoard;
import com.fanplayground.fanplayground.exception.UserNotFoundException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.BoardRepository;
import com.fanplayground.fanplayground.repository.UserBoardRepository;
import com.fanplayground.fanplayground.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    //멤버 변수 선언

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    /**
     * User 와 UserBoard 단방향 관계를 위해 사용
     */
    @Transactional
    public BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Long userId= SecurityUtil.getPrincipal().get().getId();
        /**
         * user 객체 생성 및 영속 관계 성립
         */
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("회원을 찾을 수 없습니다.")
        );

        /**
         * board 객체 생성 및 초기화
         * ( User -> Board 단방향 관계 설정 + boardName, boardColor, boardInfo 설정 )
         */
        Board board = new Board(requestDto,user);

        /**
         * userBoard 객체 생성 및 초기화
         * ( Board -> UserBoard 단방향 관계 설정 + User -> UserBoard 단방향 관계 설정 )
         */
        UserBoard userBoard = new UserBoard(user,board);

        /**
         * board 객체 및 userBoard 객체 저장
         */
        boardRepository.save(board);
        userBoardRepository.save(userBoard);

        /** Board Entity -> BoardResponseDto [ boardName, boardColor, boardInfo ]
         * BoardResponseDto 객체 반환
         */
        return new BoardCreateResponseDto(board);
    }

    public List<BoardReadAllResponseDto> ReadAllBoard() {
        /**
         * 스트림 사용 Stream <Board> -> Stream <BoardReadAllResponseDto> -> List <BoardReadAllResponseDto>
         */
        return boardRepository.findAll().stream().map(BoardReadAllResponseDto::new).toList();
    }

    public BoardReadAllResponseDto ReadChoiceBoard(Long boardId) {
        /**
         * 스트림 사용 Stream <Board> -> Stream <BoardReadAllResponseDto> -> List <BoardReadAllResponseDto>
         */
        Board board= boardRepository.findById(boardId).orElseThrow(
                ()-> new UserNotFoundException("선택하신 Board는 존재하지 않습니다.")
        );
        return new BoardReadAllResponseDto(board);

    }

    public List<BoardReadAllResponseDto> readAllUserBoard() {
        try{
            /**
             * 인증 객체로 부터 객체가 만든 보드 리스트를 스트림을 통해 보드 ID 리스트로 변환
             * 만약 아무것도 없으면 비어있는 ArrayList 반환
             */
            List<Long> userBoardIdList = SecurityUtil.getPrincipal().get().getBoards().stream().map(n->n.getBoardId()).toList();
            List<Board> boards = boardRepository.findAll();
            return boards.stream().filter(n->userBoardIdList.contains(n.getBoardId())).map(BoardReadAllResponseDto::new).toList();
        }
        catch (org.hibernate.LazyInitializationException e){
            return new ArrayList<>();
        }

    }

    public List<BoardReadAllResponseDto> readAllUserEnableBoard() {
        User user = userRepository.findById(SecurityUtil.getPrincipal().get().getId()).orElseThrow(
                ()-> new UserNotFoundException("로그인한 회원을 찾을 수 없습니다.")
        );
        /**
         * Add All 하는 과정에서 NullPointerException 발생 가능성 없애기 위해 사용
         */
        List<Long> userBoardIdList = new ArrayList<>();
        List<Long> tempList=new ArrayList<>();
        try{
            /**
             * 인증 객체로 부터 객체가 만든 보드 리스트를 스트림을 통해 보드 ID 리스트로 변환
             */
            userBoardIdList = user.getBoards().stream().map(n->n.getBoardId()).toList();
            throw new org.hibernate.LazyInitializationException("서버 자체 에러 발생 -> catch로 넘어가서 권한 이어서 확인");
        }
        catch (org.hibernate.LazyInitializationException e){
            try {
                /**
                 * 인증 객체의 User Id를 UserBoard에서 invited User Id와 일치하는 것의 invite Board Id 리스트를 반환
                 * 만약 둘다 비어있으면 비어있는 ArrayList 반환
                 */

                List<UserBoard> imsi= userBoardRepository.findAllByInviteUserId(user.getId());

                tempList=imsi.stream().map(UserBoard::getInviteBoardId)
                        .toList();
            }
            catch (org.hibernate.LazyInitializationException e2){
                return new ArrayList<>();
            }
        }
        /**
         * 두 리스트를 합친다. -> User가 사용가능한 ( 생성 + 초대 ) 보드 id 리스트
         */
        List<Long> temp = new ArrayList<>();

        if(userBoardIdList!=null||tempList!=null) {
            temp.addAll(userBoardIdList);
            temp.addAll(tempList);
        }
        else if(userBoardIdList==null&&tempList==null)
            return new ArrayList<>();
        else if (userBoardIdList==null)
            temp=tempList;
        else
            temp=userBoardIdList;

        List<Board> boards = boardRepository.findAll();

        /**
         * 기존의 userBoardIdList는 AddAll 함수로 인해 변경되었으므로 새로운 리스트를 생성
         * 스트림을 통해 최종적으로 user가 사용가능한 보드 리스트를 반환
         */
        List<Long> enableUserBoardIdList = temp;
        return boards.stream().filter(n-> enableUserBoardIdList.contains(n.getBoardId())).map(BoardReadAllResponseDto::new).toList();
    }

    /**
     * 보드에 대한 권한이 있는지 여부를 확인
     * @param LoginUser : 인증 객체
     * @param requestDto : BoardInviteRequestDto
     * @return : 0 -> 권한 없음, 1 -> 권한 있음
     */
    public int authority(User LoginUser,BoardInviteRequestDto requestDto){
        int check = 0;
        try{
            if(LoginUser.getBoards().stream().map(n->n.getBoardId()).toList()
                    .contains(requestDto.getBoardId()))
                check =1;
            throw new org.hibernate.LazyInitializationException("서버 자체 에러 발생 -> catch로 넘어가서 권한 이어서 확인");
        }
        catch (org.hibernate.LazyInitializationException e){
            try{
                if(userBoardRepository.findAllByInviteUserId(LoginUser.getId())
                        .stream().map(UserBoard::getInviteBoardId).toList()
                        .contains(requestDto.getBoardId())
                )
                    check =1;
                throw new org.hibernate.LazyInitializationException("서버 자체 에러 발생 -> catch로 넘어가서 권한 이어서 확인");
            }
            catch (org.hibernate.LazyInitializationException e2) {
                ;
            }
        }
        return check;
    }
    public BoardInviteResponseDto boardInvite(BoardInviteRequestDto requestDto) {
        User LoginUser = userRepository.findById(SecurityUtil.getPrincipal().get().getId()).orElseThrow(
                ()-> new UserNotFoundException("로그인한 회원을 찾을 수 없습니다.")
        );

        /**
         * 초대할 회원이 존재하는지 확인
         */
        User inviteUser = userRepository.findByUsername(requestDto.getUserName()).orElseThrow(
                ()-> new UserNotFoundException("초대할 회원을 찾을 수 없습니다.")
        );

        /**
         * 초대할 보드가 존재하는지 확인
         */
        Board inviteBoard = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                ()-> new UserNotFoundException("초대할 보드를 찾을 수 없습니다."));

        /**
         * 초대할 보드에 대한 권한이 있는지 여부를 확인
         */
        int check =authority(LoginUser,requestDto);
        if (check == 1) {
            userBoardRepository.save(new UserBoard(inviteUser.getId(), inviteBoard.getBoardId()));
            return new BoardInviteResponseDto("초대 되었습니다.");
        } else {
            return new BoardInviteResponseDto("당신에게는 권한이 없습니다.");
        }
    }
    @Transactional
    public BoardInviteResponseDto boardUpdate(Long boardId,BoardCreateRequestDto requestDto) {
        /**
         * 초대할 보드가 존재하는지 확인
         */
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new UserNotFoundException("초대할 보드를 찾을 수 없습니다."));

        /**
         * 로그인 회원에 대한 정보 확인
         */
        User LoginUser = userRepository.findById(SecurityUtil.getPrincipal().get().getId()).orElseThrow(
                ()-> new UserNotFoundException("로그인한 회원을 찾을 수 없습니다.")
        );

        int check = authority(LoginUser,new BoardInviteRequestDto(boardId,LoginUser.getUsername()));
        if(check==1)
            board.update(requestDto);
        return new BoardInviteResponseDto("수정되었습니다.");
    }

    public BoardInviteResponseDto deleteBoard(Long boardId) {
        /**
         * 초대할 보드가 존재하는지 확인
         */
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new UserNotFoundException("삭제할 보드를 찾을 수 없습니다."));

        /**
         * 로그인 회원이 해당 보드에 대한 삭제할 권한이 있는지 여부 확인
         */
        User LoginUser = userRepository.findById(SecurityUtil.getPrincipal().get().getId()).orElseThrow(
                ()-> new UserNotFoundException("로그인한 회원을 찾을 수 없습니다.")
        );

        if(LoginUser.getBoards().stream().map(n->n.getBoardId()).toList().contains(boardId)) {
            boardRepository.delete(board);
            return new BoardInviteResponseDto("보드가 삭제되었습니다.");
        }
        else
            return new BoardInviteResponseDto("당신에게는 권한이 없습니다.");

    }
}