package com.fanplayground.fanplayground.service;

import com.fanplayground.fanplayground.dto.MessageDto;
import com.fanplayground.fanplayground.dto.SignupRequestDto;
import com.fanplayground.fanplayground.dto.UserUpdateRequestDto;
import com.fanplayground.fanplayground.dto.UserUpdateResponseDto;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.exception.DuplicateUsernameException;
import com.fanplayground.fanplayground.repository.UserRepository;
import com.fanplayground.fanplayground.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @SpringBootTest는 Spring Boot 테스트 프레임워크의 일부
 * Spring Boot 애플리케이션의 통합 테스트를 쉽게 작성하고 수행할 수 있도록 돕는 어노테이션이당.
 * 이 어노테이션을 사용하면 Spring Boot 애플리케이션의 전체 컨텍스트가 로드되며,
 * 그 결과 애플리케이션의 모든 구성 요소가 실제 실행 환경과 거의 동일한 방식으로 작동하는 테스트 환경이 생성된다.. 그럼 테스트 속도 느려진다.
 * 쓰는 이유 = h2인라인 DB -> 실제 DB 영향 축소된다.
 * 외부 시스템과 통합을 막기 위해
 * 다양한 환경에 대해 테스트 가능 <- 다양한 구성을 통해서
 */
@SpringBootTest
/**
 * 테스트 환경에서 쓸 설정 파일 지정
 */
@TestPropertySource("classpath:test-application.properties")
/**
 * 각 테스트 시작에 데이터 생성
 * 테스트 종료 이후 데이터 삭제
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SqlGroup({
        @Sql(value="/sql/userServiceTestData.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value="/sql/userServiceTestDataDelete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
    void escapeTest() {
        // given
        // when
        ResponseEntity<MessageDto> result =userService.escape();
        // then
        assertThat(result).isEqualTo(new ResponseEntity<>(new MessageDto("회원 삭제 성공"), null, HttpStatus.OK));
    }

    @Test
    void updateTest(){
        // given
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .nickName("nickname2")
                .build();
        // when
        UserUpdateResponseDto result =userService.update(requestDto);
        // then
        assertThat(result.getNickName()).isEqualTo("nickname2");
        assertThat(result.getMsg()).isEqualTo("회원정보가 수정되었습니다");
    }



    @Test
    public void 유저_이름이_중복된_유저를_보면_예외를_던진다() {
        // given
        String name="bin0016";

        // when
        // then
        assertThrows(DuplicateUsernameException.class, () -> userService.userNameCheck(name));
    }

    @Test
    public void 유저_이름이_중복되지_않는_유저를_보면_아무것도_안_던진다() {
        // given
        String name="bin0099";

        // when
        // then
        assertDoesNotThrow(() -> userService.userNameCheck(name));
    }

    @Test
    public void getUserRoleEnum_관리자가_아닌_유저일때_유저반환() {
        // given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0017")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickName("nickname")
                .admin(false)
                .adminToken("").build();


        // when
        UserRoleEnum result = userService.getUserRoleEnum(requestDto);

        // then
        assertThat(result).isEqualTo(UserRoleEnum.USER);
    }

    @Test
    public void getUserRoleEnum_관리자인데_토큰이_일치하지_않을때_예외_반환() {
        // given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0017")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickName("nickname")
                .admin(true)
                .adminToken("").build();

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> userService.getUserRoleEnum(requestDto));
    }

    @Test
    public void getUserRoleEnum_관리자인데_관리자_토큰이_일치할때_관리자_반환() {
        // given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0017")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickName("nickname")
                .admin(true)
                .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC").build();


        // when
        UserRoleEnum result = userService.getUserRoleEnum(requestDto);

        // then
        assertThat(result).isEqualTo(UserRoleEnum.ADMIN);
    }





    @Test
    void passwordCheck_패스워드가_일치할때() {
        //given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0017")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickName("nickname")
                .admin(true)
                .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC").build();

        //when
        //then
        assertDoesNotThrow(() -> userService.passwordCheck(requestDto));
    }

    @Test
    void passwordCheck_패스워드가_불일치할때() {
        //given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0017")
                .password1("Bin@12345")
                .password2("Bin@12346")
                .nickName("nickname")
                .admin(true)
                .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC").build();

        //when
        //then
        assertThrows(IllegalArgumentException.class,() -> userService.passwordCheck(requestDto));
    }

    @Test
    void signup_회원가입_되는지_확인() {
        //given
        SignupRequestDto requestDto= SignupRequestDto.builder()
                .username("bin0036")
                .password1("Bin@12345")
                .password2("Bin@12345")
                .nickName("nickname3")
                .admin(true)
                .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC").build();

        //when
        ResponseEntity<MessageDto> result =userService.signup(requestDto);
        System.out.println("--------------------");
        //then
        assertThat(result).isEqualTo(new ResponseEntity<>(new MessageDto("회원가입 성공"), null, HttpStatus.OK));
    }


//
//    @Test
//    void update() {
//    }
    // update(), escape() 둘다 회원정보를 인증객체에서 가지고 오기에 ......

    @Test
    void getUser_검색한_유저_반환_확인() {
        // given
        Long id = 21L;

        // when
        User result = userService.getUser(id);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void getUser_검색한_유저_없을때_예외_반환확인() {
        // given
        Long id = 20L;
        // when
        // then
        assertThrows(IllegalArgumentException.class,()->userService.getUser(id));
    }



}