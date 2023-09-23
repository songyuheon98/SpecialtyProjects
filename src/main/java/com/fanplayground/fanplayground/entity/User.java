package com.fanplayground.fanplayground.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "이름은 필수 값 입니다.")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "비밀번호는 필수 값 입니다. ")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "nickname", nullable = false)
    @NotBlank(message = "닉네임은 필수 값 입니다.")
    private String nickName;


    /**
     * User : Board = 1 : n
     */
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private List<Board> boards = new ArrayList<>();

    /**
     * User : userBoard = 1 : n
     */
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private List<UserBoard> userBoards = new ArrayList<>();




    public User(String username, String password, UserRoleEnum role,String nickname) {
        this.nickName = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void addBoardList(Board board){
        this.boards.add(board);

    }
    public void addUserBoardList(UserBoard userBoard){
        this.userBoards.add(userBoard);
    }
}

