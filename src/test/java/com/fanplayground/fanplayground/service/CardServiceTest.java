package com.fanplayground.fanplayground.service;


import com.fanplayground.fanplayground.dto.CardRequestDto;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.repository.BoardColumnRepository;
import com.fanplayground.fanplayground.repository.CardRepository;
import com.fanplayground.fanplayground.repository.UserRepository;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardColumnRepository boardColumnRepository;

    @Mock
    private CardRepository cardRepository;

//    @Mock
//    private SecurityUtil securityUtil;
//    @Mock
//    private User user;

//    private static MockedStatic<SecurityUtil> mockedSecurityUtil;
//    @BeforeAll
//    static void setUp() {
//        mockedSecurityUtil = mockStatic(SecurityUtil.class);
//    }
//    @AfterAll
//    static void tearDown() {
//        mockedSecurityUtil.close();
//    }

    //    @MockBean
    @Spy
    @InjectMocks
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

//    User securityUserTestFail() {
//        User user = new User();
//        user.setId(21L);
//        user.setNickName("failnick");
//        user.setUsername("fail1234");
//        user.setPassword("fail1234");
//        user.setRole(UserRoleEnum.USER);

//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return null;
//    }
//
//    User adminSecurityUserTest() {
//        User user = new User();
//        user.setId(21L);
//        user.setNickName("adminnick");
//        user.setUsername("admin1234");
//        user.setPassword("admin1234");
//        user.setRole(UserRoleEnum.ADMIN);
//
//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return userDetails.getUser();
//    }


    @Test
    public void createTest() throws Exception{
        // given

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

        cardService = new CardService(cardRepository, boardColumnRepository);


        Date date = new Date();
        CardRequestDto requestDto = CardRequestDto.builder()
                .cardId(1L)
                .cardNo(1L)
                .columnId(1L)
                .boardId(1L)
                .cardColor("color")
                .cardName("name")
                .deadLine(date)
                .cardInfo("info")
                .build();


        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setCards(cardList);
        boardColumn.setColumnNo(1L);
        boardColumn.setColumnId(1L);
        boardColumn.setColumnName("namedd");

        // when


        given(cardRepository.save(any(Card.class))).willReturn(card);

        given(boardColumnRepository.findByColumnId(any(Long.class))).willReturn(Optional.of(boardColumn));

        // then

        Card result = cardService.createCard(requestDto,securityUserTest());

        assertEquals(result.getCardName(),"name");


    }

    @Test
    @Transactional
    public void updateTestSuc() throws Exception{
        // given

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

        cardService = new CardService(cardRepository, boardColumnRepository);


        Date date = new Date();
        CardRequestDto requestDto = CardRequestDto.builder()
                .cardId(1L)
                .cardNo(1L)
                .columnId(1L)
                .boardId(1L)
                .cardColor("color")
                .cardName("Update")
                .deadLine(date)
                .cardInfo("info")
                .build();


        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setCards(cardList);
        boardColumn.setColumnNo(1L);
        boardColumn.setColumnId(1L);
        boardColumn.setColumnName("namedd");

        // when

        given(cardRepository.findByCardIdAndNickName(any(Long.class),any(String.class))).willReturn(Optional.of(card));

        // then

        Card result = cardService.updateCard(requestDto,securityUserTest());

        assertEquals(result.getCardName(),"Update");


    }

    @Test
    @Transactional
    public void deleteTestSuc() throws Exception{
        // given

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

        cardService = new CardService(cardRepository, boardColumnRepository);


        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setCards(cardList);
        boardColumn.setColumnNo(1L);
        boardColumn.setColumnId(1L);
        boardColumn.setColumnName("namedd");

        // when

        given(cardRepository.findByCardIdAndNickName(any(Long.class),any(String.class))).willReturn(Optional.of(card));

        // then

        String result = cardService.deleteCard(card.getCardId(), securityUserTest());

        assertNotNull(result);


    }




//    @Test
//    @Transactional
//    public void updateTestFail() throws Exception{
//        // given
//
//        Card card = new Card();
//        card.setCardNo(1L);
//        card.setCardId(1L);
//        card.setCardColor("ddd");
//        card.setCardInfo("wwwww");
//        card.setNickName("nick");
//        card.setCardName("name");
//
//        List<Card> cardList = new ArrayList<>();
//
//        for(int i = 0; i < 3; i++){
//            cardList.add(card);
//        }
//
//        cardService = new CardService(cardRepository, boardColumnRepository);
//
//
//        Date date = new Date();
//        CardRequestDto requestDto = CardRequestDto.builder()
//                .cardId(1L)
//                .cardNo(1L)
//                .columnId(1L)
//                .boardId(1L)
//                .cardColor("color")
//                .cardName("Update")
//                .deadLine(date)
//                .cardInfo("info")
//                .build();
//
//
//        BoardColumn boardColumn = new BoardColumn();
//        boardColumn.setCards(cardList);
//        boardColumn.setColumnNo(1L);
//        boardColumn.setColumnId(1L);
//        boardColumn.setColumnName("namedd");
//
//        // when
//
//        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException("로그인한 회원을 찾을 수 없습니다."));
//
//
//
//
//    }




}
