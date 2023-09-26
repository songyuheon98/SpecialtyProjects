package com.fanplayground.fanplayground.controller.serviceController;


import com.fanplayground.fanplayground.dto.comment.CommentRequestDto;
import com.fanplayground.fanplayground.entity.Comment;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card/comment")
@Slf4j(topic = "commentController")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public Comment creatComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.creatComment(requestDto,userDetails.getUser());
    }

    @PutMapping
    public Comment updateComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(requestDto,userDetails.getUser());
    }
    @DeleteMapping
    public String deleteComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(requestDto.getCommentId(), userDetails.getUser());
    }
}
