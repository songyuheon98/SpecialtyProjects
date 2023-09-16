//package com.fanplayground.fanplayground.dto;
//
//import com.fanplayground.fanplayground.entity.Comment;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//@Getter
//public class CommentResponseDto {
//
//    private Long id;
//    private String content;
//    private String username;
//    private Long likeCount;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//
//
//    public CommentResponseDto(Comment saveComment) {
//        this.id = saveComment.getId();
//        this.content = saveComment.getContent();
//        this.username = saveComment.getUsername();
//        this.createdAt = saveComment.getCreatedAt();
//        this.modifiedAt = saveComment.getModifiedAt();
//        this.likeCount = saveComment.getLikeCount();
//    }
//}
