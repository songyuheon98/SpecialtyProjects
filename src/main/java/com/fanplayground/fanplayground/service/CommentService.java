package com.fanplayground.fanplayground.service;


import com.fanplayground.fanplayground.dto.CommentRequestDto;
import com.fanplayground.fanplayground.dto.CommentResponseDto;
import com.fanplayground.fanplayground.entity.*;
import com.fanplayground.fanplayground.exception.UserNotFoundException;
import com.fanplayground.fanplayground.exception.WriterNotMatchException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.CommentRepository;
import com.fanplayground.fanplayground.repository.PostRepository;
import com.fanplayground.fanplayground.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> createComment(CommentRequestDto requestDto, String tokenValue) {
        User principal = SecurityUtil.getPrincipal().get();
        String username = principal.getUsername();
        //RequestDto -> Entity
        Comment comment = new Comment(requestDto, username);
        //DB 저장
        Comment saveComment = commentRepository.save(comment);

        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
        post.addCommentList(comment);
        System.out.println("게시글에 댓글이 추가되었습니다.");

//        Post savePost = postRepository.save(post); // transaction이 없기에 save로 변경 사항 적용
        //Entity -> ResponseDto
        return new ResponseEntity<>(new CommentResponseDto(saveComment),null, HttpStatus.OK );
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto requestDto, String tokenValue) {
        User principal = SecurityUtil.getPrincipal().get();

        // 해당 유저의 댓굴 id 값이 DB 에 존재하는지 확인
        Optional<Comment> checkComment = commentRepository.findById(id);
        Comment comment;

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(()->
                new UserNotFoundException("회원을 찾을 수 없습니다.")
        );

        if (checkComment.isPresent()) {
            comment = checkComment.get();
        } else{
            return new ResponseEntity<>(new Message("comment란은 비워두면 안됩니다.",400), null, HttpStatus.BAD_REQUEST);
        }
        System.out.println(user.getRole());
        if(user.getRole().equals(UserRoleEnum.ADMIN)){

        } else if(!comment.getUsername().equals(user.getUsername()) ){
            throw new WriterNotMatchException("작성자만 삭제/수정할 수 있습니다.");
        }


         // comment 수정
        comment.update(requestDto);

        return new ResponseEntity<>(new CommentResponseDto(comment),null, HttpStatus.OK );
    }

    public ResponseEntity<?> deleteComment(Long id, String tokenValue) {

        Message msg = new Message("댓글 삭제 성공", 200);

        User principal = SecurityUtil.getPrincipal().get();

        // 해당 유저의 댓굴 id 값이 DB 에 존재하는지 확인
        Optional<Comment> checkComment = commentRepository.findById(id);
        Comment comment;

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(()->
                new UserNotFoundException("회원을 찾을 수 없습니다.")
        );

        if (checkComment.isPresent()) {
            comment = checkComment.get();
        } else {
            return new ResponseEntity<>(new Message( "댓글 상태가 이상합니다.",400), null, HttpStatus.BAD_REQUEST);
        }
        if(user.getRole().equals(UserRoleEnum.ADMIN)){
        } else if(!comment.getUsername().equals(user.getUsername()) ){
            throw new WriterNotMatchException("작성자만 삭제/수정할 수 있습니다.");
        }
        // comment 삭제
        commentRepository.delete(comment);

        return new ResponseEntity<>(msg, null, HttpStatus.OK);
    }

//    private void findPost(Long id){
//        //findById -> Optional type -> Null Check
//        postRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
//        );
//    }
}
