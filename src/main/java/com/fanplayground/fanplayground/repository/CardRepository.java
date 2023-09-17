package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findByCardId(Long cardId);

    Optional<Card> findByCardIdAndNickName(Long cardId, String nickName);
}
