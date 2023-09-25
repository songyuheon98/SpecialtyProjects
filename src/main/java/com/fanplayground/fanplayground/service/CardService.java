package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.CardRequestDto;
import com.fanplayground.fanplayground.dto.CardResponseDto;
import com.fanplayground.fanplayground.entity.BoardColumn;
import com.fanplayground.fanplayground.entity.Card;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.repository.BoardColumnRepository;
import com.fanplayground.fanplayground.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "cardService")
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardColumnRepository boardColumnRepository;


    @Transactional
    public Card createCard(CardRequestDto requestDto, User user) {
        log.info("카드 생성");
        Card card = new Card(requestDto, user.getNickName());
        System.out.println("card.getCardName() = " + card.getCardName());
        Card saveCard = cardRepository.save(card);
        System.out.println("saveCard.getCardName() = " + saveCard.getCardName());
        System.out.println("requestDto.getColumnId() = " + requestDto.getColumnId());

        isValueColumn(requestDto,saveCard);

        log.info("카드 생성 db 저장");
        return saveCard;
    }

    @Transactional
    public void isValueColumn (CardRequestDto requestDto,Card card){
        BoardColumn boardColumn = boardColumnRepository.findByColumnId(requestDto.getColumnId()).orElseThrow(() ->
                new NullPointerException("해당 컬럼은 없음")
        );

        boardColumn.addCard(card);
    }

    @Transactional
    public Card updateCard(CardRequestDto requestDto, User user) {
        log.info("카드 수정");
        Card card = findCard(user.getNickName(), requestDto.getCardId(), user.getRole().toString());

        card.update(requestDto);
        log.info("");
        return card;
    }

    public String deleteCard(Long cardId, User user) {
        log.info("카드 삭제");
        Card card = findCard(user.getNickName(), cardId, user.getRole().toString());

        cardRepository.delete(card);
        return "succ";
    }

    private Card findCard(String nickName, Long cardId, String role) {

        if (role.equals("ADMIN")) {
            return cardRepository.findByCardId(cardId).orElseThrow(
                    () -> new NullPointerException("발견값 없음")
            );
        }
        return cardRepository.findByCardIdAndNickName(cardId, nickName).orElseThrow(
                () -> new NullPointerException("해당카드는 없음")
        );

    }

    public CardResponseDto readCard(Long cardId){
        Card card = cardRepository.findByCardId(cardId).orElseThrow(
                () -> new NullPointerException("해당 카드는 없음")
        );
        return new CardResponseDto(card);
    }
}

