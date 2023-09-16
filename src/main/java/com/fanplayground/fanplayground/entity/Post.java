package com.fanplayground.fanplayground.entity;

import com.fanplayground.fanplayground.dto.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "post") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likecount", nullable = false)
    private Long likeCount = 0L;

    @Column(name = "foldernumber", nullable = false)
    private Long folderNumber;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Post : folder = n : 1
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;


    public Post(PostRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
//    public void addCommentList(Comment comment){
//        this.comments.add(comment);
//        comment.setPost(this);
//    }


}
