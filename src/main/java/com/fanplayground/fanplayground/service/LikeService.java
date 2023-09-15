package com.fanplayground.fanplayground.service;



import com.fanplayground.fanplayground.entity.*;
import com.fanplayground.fanplayground.repository.CommentLikeRepository;
import com.fanplayground.fanplayground.repository.CommentRepository;
import com.fanplayground.fanplayground.repository.PostLikeRepository;
import com.fanplayground.fanplayground.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<?> createLikePost(Long postId, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );

        PostLike temp = postLikeRepository.findByUserIdAndPostId(userId,postId).orElse(null);
        if(temp==null) {
            postLikeRepository.save(new PostLike(postId,userId));
            post.setLikeCount(post.getLikeCount() + 1);
        }
        else {
            post.setLikeCount(post.getLikeCount() - 1);
            postLikeRepository.delete(temp);
        }
        return new ResponseEntity<>(new Message("게시글 좋아요 클릭 성공",200), null, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> createLikeComment(Long postId, Long commentId, Long userId) {
        /**
         * 좋아요가 눌릴 댓글이 존재하는 지 여부를 확인
         */
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );
        CommentLike temp =commentLikeRepository.findByUserIdAndCommentId(userId,commentId).orElse(null);
        if(temp==null) {
            commentLikeRepository.save(new CommentLike(postId,commentId,userId));
            comment.setLikeCount(comment.getLikeCount() + 1);
        }
        else {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentLikeRepository.delete(temp);
        }

        return new ResponseEntity<>(new Message("댓글 좋아요 클릭 성공",200), null, HttpStatus.OK);

    }

}
