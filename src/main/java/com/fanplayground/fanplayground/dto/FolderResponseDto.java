package com.fanplayground.fanplayground.dto;

import com.fanplayground.fanplayground.entity.Folder;
import com.fanplayground.fanplayground.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class FolderResponseDto {

    private Long id;

    private Long folderNumber;

    private List<Post> posts;

    public FolderResponseDto(Folder folder){
        this.id = folder.getId();
        this.folderNumber = folder.getFolderNumber();
        this.posts=folder.getPosts();
    }
}
