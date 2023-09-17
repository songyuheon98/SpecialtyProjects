package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.CommentRequestDto;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j(topic = "commentController")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/card/comment")
    public ResponseEntity creatComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("creatComment시작");
        return commentService.creatComment(requestDto,userDetails.getUser());
    }

    @PutMapping("/card/comment")
    public ResponseEntity updateComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("updateComment시작");
        return commentService.updateComment(requestDto,userDetails.getUser());
    }

    @DeleteMapping("/card/comment")
    public ResponseEntity deleteComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("deleteComment시작");
        return commentService.deleteComment(requestDto.getCommentId(), userDetails.getUser());
    }

}
