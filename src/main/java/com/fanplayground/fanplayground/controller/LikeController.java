package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/post/{postId}")
    public ResponseEntity<?> createLikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return likeService.createLikePost(postId,userId);
    }


    @PostMapping("/like/comment/{postId}/{commentId}")
    public ResponseEntity<?> createLikeComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return likeService.createLikeComment(postId,commentId,userId);
    }
}
