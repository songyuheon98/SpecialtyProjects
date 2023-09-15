package com.fanplayground.fanplayground.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class PostResponseListDto {
    List<PostResponseDto> postList = new ArrayList<>();

    public PostResponseListDto(List<PostResponseDto> post){
        this.postList.addAll(post);
    }
}
