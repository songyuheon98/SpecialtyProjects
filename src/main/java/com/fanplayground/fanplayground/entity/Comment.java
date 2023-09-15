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
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "likecount", nullable = false)
    private Long likeCount= 0L;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    @JsonBackReference //
    private List<Reply> replys = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, String username) {
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void addReplyList(Reply reply){
        this.replys.add(reply);
        reply.setComment(this);
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    // post가 출력이 안되어야함. dto 하나 만들어서 post 빼고 입력받고 Responsedto에도 해당 dto list 가져옴
}
