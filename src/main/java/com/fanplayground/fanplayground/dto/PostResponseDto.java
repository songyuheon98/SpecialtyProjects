//package com.fanplayground.fanplayground.dto;
//
//
//import com.fanplayground.fanplayground.entity.Comment;
//import com.fanplayground.fanplayground.entity.Post;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//@Getter
//public class PostResponseDto{
//
//
//    private Long id;
//    private String title;
//    private String username;
//    private String content;
//    private Long likeCount;
//    private Long folderNumber;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//    private List<ForResponseComment> comments = new ArrayList<>();
////    private List<CommentResponseDto> commentResponseDto = new ArrayList<>();
//
//    public PostResponseDto(Post post) {
//        Comparator<ForResponseComment> comparator = new Comparator<ForResponseComment>() {
//            @Override
//            public int compare(ForResponseComment o1, ForResponseComment o2) {
//                if(o1.getCreatedAt().isAfter(o2.getCreatedAt())) return 1;
//                else return -1;
//                // return o1 - o2 ;
//            }
//        };
//
//        this.id = post.getId();
//        this.title = post.getTitle();
//        this.username = post.getUsername();
//        this.content = post.getContent();
//        this.likeCount = post.getLikeCount();
//        this.createdAt = post.getCreatedAt();
//        this.folderNumber = post.getFolderNumber();
//        this.modifiedAt = post.getModifiedAt();
//        StringBuilder sb = new StringBuilder();
//        List<Comment> commentlist = new ArrayList<>(post.getComments());
//
//        for(Comment comment : post.getComments()){
//            ForResponseComment cm = new ForResponseComment(comment);
//            comments.add(cm);
//        }
//        Collections.sort(comments, comparator);
//
//
//    }
//
//}
