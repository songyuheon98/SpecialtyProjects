package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.CardRequestDto;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CardService cardService;

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
    void createCard() throws Exception{
        Date date = new Date();
        CardRequestDto requestDto = CardRequestDto.builder()
                .cardId(1L)
                .cardColor("blue")
                .cardNo(1L)
                .boardId(1L)
                .cardInfo("info")
                .deadLine(date)
                .cardName("name")
                .build();

        when(cardService.createCard(any(CardRequestDto.class),any(User.class))).thenReturn(
                Card.builder()
                        .cardId(1L)
                        .cardColor("blue")
                        .cardNo(1L)
                        .cardInfo("info")
                        .deadLine(date)
                        .nickName("nick")
                        .build()
        );

        mvc.perform(post("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void updateCard() throws Exception{
        Date date = new Date();
        CardRequestDto requestDto = CardRequestDto.builder()
                .cardId(1L)
                .cardColor("blue")
                .cardNo(1L)
                .boardId(1L)
                .cardInfo("info")
                .deadLine(date)
                .cardName("update")
                .build();

        when(cardService.updateCard(any(CardRequestDto.class),any(User.class))).thenReturn(
                Card.builder()
                        .cardId(1L)
                        .cardColor("blue")
                        .cardNo(1L)
                        .cardInfo("updated")
                        .deadLine(date)
                        .nickName("nick")
                        .build()
                );

        mvc.perform(put("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteCard() throws Exception{

        when(cardService.deleteCard(any(Long.class),any(User.class))).thenReturn("succ");

        mvc.perform(delete("/api/card/{cardId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(securityUserTest()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }




}
