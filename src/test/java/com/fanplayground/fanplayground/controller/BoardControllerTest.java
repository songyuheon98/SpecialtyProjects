package com.fanplayground.fanplayground.controller;

import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test-application.properties")
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value="/sql/userServiceTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/userServiceTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class BoardControllerTest {

    @BeforeEach
    void securityUserTest() {
        User user = new User();
        user.setId(21L);
        user.setNickName("nickname");
        user.setUsername("bin0016");
        user.setPassword("Bin@12345");
        user.setRole(UserRoleEnum.USER);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    @Test
    void createBoard() {

    }

    @Test
    void readAllBoard() {
    }

    @Test
    void readChoiceBoard() {
    }

    @Test
    void readAllUserBoard() {
    }

    @Test
    void readAllUserEnableBoard() {
    }

    @Test
    void boardInvite() {
    }

    @Test
    void boardUpdate() {
    }

    @Test
    void deleteBoard() {
    }
}