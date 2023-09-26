package com.fanplayground.fanplayground.entity;

import com.fanplayground.fanplayground.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "commentInfo", nullable = false)
    private String commentInfo;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "nickName", nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


    public Comment(CommentRequestDto requestDto, User user,Card cardId) {
        this.commentInfo = requestDto.getCommentInfo();
        this.userId = user.getId();
        this.nickName = user.getNickName();
        this.card = cardId;
    }

    public void update(CommentRequestDto requestDto, User user) {
        this.commentInfo = requestDto.getCommentInfo();
        this.userId = user.getId();
        this.nickName = user.getNickName();
    }
}
