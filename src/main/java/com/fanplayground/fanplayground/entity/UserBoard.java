package com.fanplayground.fanplayground.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_board_id")
    private Long userBoardId;

    /**
     * user : userBoard = 1 : n
     * userBoard : board = n : 1
     */

    public UserBoard(User user, Board board) {
        user.addUserBoardList(this);
        board.addUserBoardList(this);
    }
}
