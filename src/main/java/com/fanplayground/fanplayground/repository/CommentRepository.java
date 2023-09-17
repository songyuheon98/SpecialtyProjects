package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByCommentIdAndNickName(Long id, String nickName);

    Optional<Comment> findByCommentId(Long id);
}
