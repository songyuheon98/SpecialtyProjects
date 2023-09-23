package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.CommentRequestDto;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.Comment;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    User securityUserTest() {
        User user = new User();
        user.setId(21L);
        user.setNickName("nickname");
        user.setUsername("user1234");
        user.setPassword("user1234");
        user.setRole(UserRoleEnum.USER);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails.getUser();
    }

    @Test
    void createComment() throws Exception{

        Card card = new Card();
        card.setCardNo(1L);
        card.setCardId(1L);
        card.setCardColor("ddd");
        card.setCardInfo("wwwww");
        card.setNickName("nick");
        card.setCardName("name");

        List<Card> cardList = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            cardList.add(card);
        }

        CommentRequestDto requestDto = CommentRequestDto.builder()
                .cardId(card)
                .commentInfo("infoinfo")
                .commentId(1L)
                .build();

        when(commentService.creatComment(any(CommentRequestDto.class),any(User.class))).thenReturn(
                Comment.builder()
                        .commentId(1L)
                        .commentInfo("commentInfo")
                        .card(card)
                        .nickName("nick")
                        .userId(1L)
                        .build()
        );

        mvc.perform(post("/api/card/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateComment() throws Exception{

        Card card = new Card();
        card.setCardNo(1L);
        card.setCardId(1L);
        card.setCardColor("ddd");
        card.setCardInfo("wwwww");
        card.setNickName("nick");
        card.setCardName("name");

        List<Card> cardList = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            cardList.add(card);
        }

        CommentRequestDto requestDto = CommentRequestDto.builder()
                .cardId(card)
                .commentInfo("infoinfo")
                .commentId(1L)
                .build();

        when(commentService.updateComment(any(CommentRequestDto.class),any(User.class))).thenReturn(
                Comment.builder()
                        .commentId(1L)
                        .commentInfo("commentInfo")
                        .card(card)
                        .nickName("nick")
                        .userId(1L)
                        .build()
        );

        mvc.perform(put("/api/card/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void deleteCard() throws Exception{

        CommentRequestDto requestDto = CommentRequestDto.builder()
                .commentId(1L)
                .build();

        when(commentService.deleteComment(any(Long.class),any(User.class))).thenReturn(
                "succ"
        );

        mvc.perform(delete("/api/card/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}
