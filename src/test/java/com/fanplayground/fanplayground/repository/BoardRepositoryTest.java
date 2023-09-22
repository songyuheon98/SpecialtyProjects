package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.Board;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:test-application.properties")
@DataJpaTest(showSql = true)
@SqlGroup({
        @Sql(value="/sql/BoardRepositoryTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/BoardRepositoryTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Test
    void BoardRepository_연결_확인(){
        //given
        Board board = new Board();
        board.setBoardId(101L);
        board.setBoardName("boardName2");
        board.setBoardColor("boardColor2");
        board.setBoardInfo("boardInfo2");

        //when
        Board result = boardRepository.save(board);

        //then
        assertThat(result).isNotNull();

    }
}
