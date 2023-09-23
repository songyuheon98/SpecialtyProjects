package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:test-application.properties")
@DataJpaTest(showSql = true)
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void boardRepository_연결_확인() {
        //given
        Board board = Board.builder()
                .boardName("boardName")
                .boardColor("boardColor")
                .boardInfo("boardInfo")
                .build();
        //when
        Board result = boardRepository.save(board);

        //then
        assertThat(result).isNotNull();
    }
}
