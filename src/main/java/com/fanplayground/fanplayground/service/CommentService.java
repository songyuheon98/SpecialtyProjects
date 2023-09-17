package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.CommentRequestDto;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.Comment;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.repository.CardRepository;
import com.fanplayground.fanplayground.repository.CommentRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j(topic = "commentService")
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public ResponseEntity creatComment(CommentRequestDto requestDto, User user) {
        log.info("댓글 생성");
        // 카드 존재 여부 확인
        hasCard(requestDto.getCardId().getCardId());

        Comment comment = new Comment(requestDto, user, requestDto.getCardId());

        Comment saveComment = commentRepository.save(comment);

        return new ResponseEntity(saveComment, null, 200);
    }

    @Transactional
    public ResponseEntity updateComment(CommentRequestDto requestDto, User user) {
        log.info("댓글 수정");

        if (user.getRole().toString().equals("ADMIN")) {
            log.info("수정 admin으로 입장");
            Comment adminComment = commentRepository.findByCommentId(requestDto.getCardId().getCardId()).orElseThrow();
            adminComment.update(requestDto, user);
            return new ResponseEntity(adminComment, null, 200);
        }


        // 댓글 존재확인
        Comment comment = hasComment(requestDto.getCommentId(), user.getNickName());
        comment.update(requestDto, user);

        return new ResponseEntity(comment, null, 200);
    }

    public ResponseEntity deleteComment(Long commentId, User user) {
        log.info("댓글 삭제");
        if (user.getRole().toString().equals("ADMIN")) {
            log.info("삭제 admin으로 입장");
            Comment adminComment = commentRepository.findByCommentId(commentId).orElseThrow();
            commentRepository.delete(adminComment);
            return new ResponseEntity("admin이 댓글 삭제 성공", null, 200);
        }

        // 댓글 존재 확인
        Comment comment = hasComment(commentId, user.getNickName());
        commentRepository.delete(comment);
        return new ResponseEntity("댓글 삭제 성공", null, 200);
    }


    private Comment hasComment(Long commentId, String nickName) {
        return commentRepository.findByCommentIdAndNickName(commentId, nickName).orElseThrow(() ->
                new NullPointerException("해당 댓글이 존재하지 않습니다")
        );
    }

    private Card hasCard(Long cardId) {
        return cardRepository.findByCardId(cardId).orElseThrow(() ->
                new NullPointerException("해당 카드는 존재하지않습니다.")
        );
    }



}
