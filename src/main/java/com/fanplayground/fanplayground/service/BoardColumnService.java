package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.*;
import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.repository.BoardRepository;
import com.fanplayground.fanplayground.repository.BoardColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public MessageDto createBoardColumn(BoardColumnRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(() ->
                new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));

        BoardColumn boardColumn = new BoardColumn(requestDto);

        board.addUserBoardList(boardColumn);

        boardColumnRepository.save(boardColumn);

        BoardColumn boardColumnTemp = boardColumnRepository.findById(boardColumn.getColumnId())
                .orElseThrow(() -> new IllegalArgumentException("해당 컬럼이 존재하지 않습니다."));
        boardColumnTemp.update(boardColumn.getColumnId());

        return new MessageDto("해당 컬럼이 추가되었습니다");
    }

    @Transactional
    public MessageUpdateDto updateBoardColumn(BoardColumnUpdateRequestDto requestDto) {
        BoardColumn boardColumn = boardColumnRepository.findById(requestDto.getColumnId()).orElseThrow(() ->
                new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));

        boardColumn.updateName(requestDto.getColumnName());

        return new MessageUpdateDto("해당 컬럼명이 수정되었습니다", requestDto.getColumnName());
    }


    public MessageDto deleteBoardColumn(Long columnId) {
        BoardColumn boardColumn = boardColumnRepository.findById(columnId).orElseThrow(() ->
                new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));
        boardColumnRepository.deleteById(columnId);
        return new MessageDto("해당 컬럼명이 삭제되었습니다");
    }

//    public List<BoardColumnMoveResponseDto> moveBoardColumn(BoardColumnMoveRequestDto boardColumnMoveRequestDto) {
//        BoardColumn boardColumn = boardColumnRepository.findById(boardColumnMoveRequestDto.getColumnId()).orElseThrow(() ->
//                new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));
//
//        /**
//         * boardColumn 리스트
//         */
//        List<BoardColumn> moveTemp = boardColumnRepository.findAll();
//
//        /**
//         * boardColumnMoveRequestDto -> list  columnNo
//         */
//        List<BoardColumnMoveRequestEtcDto> temp2 = boardColumnMoveRequestDto.getBoardColumnMoveRequestEtcDtos();
//
//        moveTemp.stream().map(n -> n.getColumnId().equals(temp2));
//
//
//    }

    @Transactional
    public BoardColumnMoveResponseDto moveBoardColumn(Long boardId, BoardColumnMoveRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));
        List<Integer> columnsNos = requestDto.getColumnsNos();

        for (int i = 0; i < board.getBoardColumns().size(); i++)
            board.getBoardColumns().get(i).setColumnNo(Long.valueOf(columnsNos.get(i)));

//        return new BoardReadAllResponseDto(board);
        return new BoardColumnMoveResponseDto(board.getBoardColumns().stream().map(n->n.getColumnNo()).toList());
    }
}
