package com.fanplayground.fanplayground.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "commentlike")
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commentid", nullable = false)
    private Long commentId;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "postid", nullable = false)
    private Long postId;

    public CommentLike(Long postId, Long commentId, Long userId) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
    }

}