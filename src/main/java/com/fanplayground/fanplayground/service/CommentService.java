package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.comment.CommentRequestDto;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.Comment;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.repository.CardRepository;
import com.fanplayground.fanplayground.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j(topic = "commentService")
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public Comment creatComment(CommentRequestDto requestDto, User user) {
        log.info("댓글 생성");
        // 카드 존재 여부 확인
        hasCard(requestDto.getCardId().getCardId());

        Comment comment = new Comment(requestDto, user, requestDto.getCardId());

        Comment saveComment = commentRepository.save(comment);

        return saveComment;
    }

    @Transactional
    public Comment updateComment(CommentRequestDto requestDto, User user) {
        log.info("댓글 수정");

        // 댓글 존재확인
        Comment comment = hasComment(requestDto.getCommentId(), user.getNickName(), user.getRole().toString());
        comment.update(requestDto, user);

        return comment;
    }

    public String deleteComment(Long commentId, User user) {
        log.info("댓글 삭제");

        // 댓글 존재 확인
        Comment comment = hasComment(commentId, user.getNickName(), user.getRole().toString());
        commentRepository.delete(comment);
        return "succ";
    }


    private Comment hasComment(Long commentId, String nickName, String role) {
        if(role.equals("ADMIN")){
            return commentRepository.findByCommentId(commentId).orElseThrow(
                    () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
            );
        }
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
