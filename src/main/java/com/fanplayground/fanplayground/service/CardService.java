package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.CardRequestDto;
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
    public ResponseEntity createCard(CardRequestDto requestDto, User user) {
        log.info("카드 생성");
        Card card = new Card(requestDto, user.getNickName());
        Card saveCard = cardRepository.save(card);

        BoardColumn boardColumn = boardColumnRepository.findByColumnId(requestDto.getColumnId()).orElseThrow(() ->
                new NullPointerException("해당 컬럼은 없음")
        );
        boardColumn.addCard(saveCard);


        return new ResponseEntity(saveCard, null, 200);
    }

    @Transactional
    public ResponseEntity updateCard(CardRequestDto requestDto, User user) {
        log.info("카드 수정");
        if (user.getRole().toString().equals("ADMIN")) {
            log.info("admin수정");

            Card card = cardRepository.findByCardId(requestDto.getColumnId()).orElseThrow(() ->
                    new NullPointerException("발견값 없음")
            );
            card.update(requestDto);
            return new ResponseEntity(card, null, 200);
        }
        Card card = findCard(user.getNickName(), requestDto.getColumnId());
        card.update(requestDto);
        return new ResponseEntity(card, null, 200);
    }

    public ResponseEntity deleteCard(Long cardId, User user) {
        log.info("카드 삭제");
        if (user.getRole().toString().equals("ADMIN")) {
            log.info("admin삭제");

            Card card = cardRepository.findByCardId(cardId).orElseThrow(() ->
                    new NullPointerException("발견값 없음")
            );
            cardRepository.delete(card);
            return new ResponseEntity("삭제 성공", null, 200);
        }
        Card card = findCard(user.getNickName(), cardId);
        cardRepository.delete(card);
        return new ResponseEntity("삭제 성공", null, 200);
    }

    private Card findCard(String nickName, Long cardId) {

        return cardRepository.findByCardIdAndNickName(cardId, nickName).orElseThrow(
                () -> new NullPointerException("해당카드는 없음")
        );

    }


}

