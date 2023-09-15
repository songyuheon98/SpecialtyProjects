package com.fanplayground.fanplayground.entity;

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
@NoArgsConstructor
@Table(name = "folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "foldernumber", nullable = false)
    private Long folderNumber;

    @Column(name = "postid", nullable = false)
    private Long postId;

    // folder : post = 1 : n
    @OneToMany(mappedBy = "folder", cascade = CascadeType.PERSIST) // Folder 를 저장하면 Post 도 저장
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    // Folder : user = n : m
    @OneToMany(mappedBy = "folder")
    @JsonBackReference
    private List<MiddleTable> middleTableList = new ArrayList<>();


    public Folder(Post post, Long folderNumber) {
        this.folderNumber = folderNumber;
        this.postId = post.getId();
        this.posts.add(post);
        post.setFolder(this);

    }

}
