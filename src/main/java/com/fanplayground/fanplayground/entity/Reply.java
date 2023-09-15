package com.fanplayground.fanplayground.entity;

import com.fanplayground.fanplayground.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reply")
@NoArgsConstructor
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public Reply(ReplyRequestDto requestDto, String username) {
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(ReplyRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    // post가 출력이 안되어야함. dto 하나 만들어서 post 빼고 입력받고 Responsedto에도 해당 dto list 가져옴
}
