package com.fanplayground.fanplayground.entity;
import com.fanplayground.fanplayground.dto.board.createRead.BoardCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "board")
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @Column(name = "board_color", nullable = false)
    private String boardColor;

    @Column(name = "board_info", nullable = false)
    private String boardInfo;

    /**
     * Board : userBoard = 1 : n
     */
    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY )
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private List<UserBoard> userBoards = new ArrayList<>();

    /**
     * Board : BoardColumn = 1 : n
     */

    @OneToMany(orphanRemoval = true,fetch = FetchType.LAZY )
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private List<BoardColumn> boardColumns = new ArrayList<>();


    public Board(BoardCreateRequestDto requestDto, User user) {
        this.boardName = requestDto.getBoardName();
        this.boardColor = requestDto.getBoardColor();
        this.boardInfo = requestDto.getBoardInfo();
        user.addBoardList(this);
    }

    public void addUserBoardList(UserBoard userBoard) {
        this.userBoards.add(userBoard);
    }


    public void addUserBoardList(BoardColumn boardColumn) {
        this.boardColumns.add(boardColumn);
    }      
  
    public void update(BoardCreateRequestDto requestDto) {
        this.boardName = requestDto.getBoardName();
        this.boardColor = requestDto.getBoardColor();
        this.boardInfo = requestDto.getBoardInfo();
    }
}
