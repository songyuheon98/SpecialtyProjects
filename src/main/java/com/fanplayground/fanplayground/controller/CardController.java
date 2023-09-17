package com.fanplayground.fanplayground.controller;
import com.fanplayground.fanplayground.dto.CardRequestDto;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fanplayground.fanplayground.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j(topic = "cardController")
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card")
    public ResponseEntity createCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("카드 생성 컨트롤러");
        return cardService.createCard(requestDto,userDetails.getUser());
    }

    @PutMapping("/card")
        public ResponseEntity updateCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("카드 수정 컨트롤러");
        return cardService.updateCard(requestDto,userDetails.getUser());
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return cardService.deleteCard(cardId,userDetails.getUser());
    }

}
