package com.fanplayground.fanplayground.entity;

import com.fanplayground.fanplayground.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "commentInfo", nullable = false)
    private String commentInfo;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "nickName", nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;





//    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
//    @JsonBackReference //
//    private List<Reply> replys = new ArrayList<>();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private Post post;

}
