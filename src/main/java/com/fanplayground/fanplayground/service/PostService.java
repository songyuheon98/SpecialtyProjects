package com.fanplayground.fanplayground.service;


import com.fanplayground.fanplayground.dto.PageRequestDto;
import com.fanplayground.fanplayground.dto.PostRequestDto;
import com.fanplayground.fanplayground.dto.PostResponseDto;
import com.fanplayground.fanplayground.entity.*;
import com.fanplayground.fanplayground.exception.TokenNotValidException;
import com.fanplayground.fanplayground.exception.UserNotFoundException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.FolderRepository;
import com.fanplayground.fanplayground.repository.PostRepository;
import com.fanplayground.fanplayground.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    //멤버 변수 선언
    private final PostRepository postRepository;
    private final FolderRepository folderRepository;

    public List<PostResponseDto> getFolder(Long id) {
        List<Post> postList = postRepository.findByFolderNumber(id);
        return postList.stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public ResponseEntity<?> createPost(PostRequestDto requestDto, String tokenValue) {
        User principal = SecurityUtil.getPrincipal().get();
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("회원을 찾을 수 없습니다.")
        );

        //RequestDto -> Entity
        Post post = new Post(requestDto,username);
        user.addPostList(post);

        //DB 저장
        Post savePost = postRepository.save(post);

        //폴더 객체 저장
        folderRepository.save(new Folder(post,requestDto.getFolderNumber()));

        //Entity -> ResponseDto
        return new ResponseEntity<>(new PostResponseDto(savePost),null, HttpStatus.OK);
    }


    public Pageable getPageable(PageRequestDto pageRequestDto){
        // 페이징 처리
        Sort.Direction direction = pageRequestDto.getIsAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, pageRequestDto.getSortBy());
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize(), sort);
    }
    public Page<PostResponseDto> getPosts(PageRequestDto pageRequestDto){


        // 페이징 처리
//        Pageable pageable = PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize(), sort);
//
//        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
////        UserRoleEnum userRoleEnum = user.getRole();
//
//        Page<Post> postList;
//        postList = postRepository.findAll(pageable);
//
//        return postList.map(PostResponseDto::new);
        return postRepository.findAll(getPageable(pageRequestDto)).map(PostResponseDto::new);

        // comment : post  -> N : 1
        // commentList -> postId 기준으로 불러온다.
//        List<Post> postList = postRepository.findAllByOrderByCreatedAt();
//        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
//
//        for(Post post : postList){
//            PostResponseDto postRes = new PostResponseDto(post);
//            postResponseDtoList.add(postRes);
//        }
//        return new PostResponseListDto(postResponseDtoList);
        //return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPost(Long folderNumber) {
//        System.out.println("folderNumber = " + folderNumber);
//        List<PostResponseDto> temp = getPosts(pageRequestDto).stream().collect(Collectors.toList());
//
//        List<Integer> checkLong = folderRepository.findByFolderNumber(folderNumber).stream().map(n->n.getPostId().intValue()).collect(Collectors.toList());
//
//        temp = temp.stream().filter(n->checkLong.contains(n.getId().intValue())).collect(Collectors.toList());
        return postRepository.findAll().stream()
                .filter(
                        n->folderRepository.findByFolderNumber(folderNumber).stream()
                        .map(Folder::getPostId).toList()
                        .contains(n.getId())
                )
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional //변경 감지(Dirty Checking), 부모메서드인 updatePost
    public ResponseEntity<?> updatePost(Long id, PostRequestDto requestDto, String tokenValue){
        User principal = SecurityUtil.getPrincipal().get();

        // 해당 post DB에 존재하는지 확인 수정필요
        Post post = findPost(id);
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("회원을 찾을 수 없습니다.")
        );

        if(user.getRole().equals(UserRoleEnum.ADMIN)){
            log.info("관리자가 로그인 하였습니다.");
        }else if(!username.equals(post.getUsername())){
            throw new UserNotFoundException("작성자만 삭제/수정할 수 있습니다.");
        }

        // post 내용 수정
        post.update(requestDto);

        return new ResponseEntity<>(postRepository.findById(id)
                ,null, HttpStatus.OK);
    }

    public ResponseEntity<Message> deletePost(Long id, String tokenValue){

        Message msg = new Message("게시글 삭제 성공",200);

        User principal = SecurityUtil.getPrincipal().get();

        // 해당 post DB에 존재하는지 확인
        Post post = findPost(id);

        // 해당 사용자(username)가 작성한 게시글인지 확인
        // setSubject(username)
        String username = principal.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new TokenNotValidException("토큰이 유효하지 않습니다.")
        );
        if(user.getRole().equals(UserRoleEnum.ADMIN)){
            System.out.println("운영자가 로그인하였습니다.");
        }else if(!username.equals(post.getUsername())){
            throw new UserNotFoundException("회원을 찾을 수 없습니다.");
        }

        //post 삭제
        postRepository.delete(post);

        return new ResponseEntity<>(msg, null, HttpStatus.OK);
    }

    private Post findPost(Long id){
        //findById -> Optional type -> Null Check
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
