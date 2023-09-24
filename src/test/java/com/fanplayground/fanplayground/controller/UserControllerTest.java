package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.SignupRequestDto;
import com.fanplayground.fanplayground.dto.UserUpdateRequestDto;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test-application.properties")
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value="/sql/userServiceTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/userServiceTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    void 사용자_회원가입_성공() throws Exception {
        // given
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("bin0222")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickname("nickname")
                .build();
        // when
        // then

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("회원가입 성공"));
//                .andExpect(jsonPath("$.msg").value("회원가입이 완료되었습니다."));
    }

    @Test
    void escapeTest() throws Exception {
        mockMvc.perform(delete("/api/auth/escape"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("회원 삭제 성공"));
    }

    @Test
    void update() throws Exception {
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .nickName("nickname2")
                .build();
        // when
        // then

        mockMvc.perform(put("/api/auth/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.nickName")).value("nickname2"))
                .andExpect(jsonPath(("$.msg")).value("회원정보가 수정되었습니다"));

    }
}