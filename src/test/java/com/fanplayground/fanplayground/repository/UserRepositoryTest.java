package com.fanplayground.fanplayground.repository;

import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @DataJpaTest는 Spring Boot에서 제공하는 애너테이션
 * JPA 구성요소만을 로드하여 데이터 접근 계층의 테스트에 초점을 맞춘 테스트를 수행하게 해준다.
 * 즉 이 애너테이션은 JPA 관련 구성을 스캔하고 빈(bean)으로 등록하여 테스트 컨텍스트를 작성하게 한다.
 * @DataJpaTest는 H2, HSQL, Derby와 같은 내장 데이터베이스를 사용하려고 시도한다.
 * @AutoConfigureTestDatabase 애너테이션을 사용하여 이러한 동작을 변경할 수 있다.
 * Plus showSql 속성을 true로 설정하면, SQL 쿼리가 로그로 출력한다.
 */
@DataJpaTest(showSql = true)
@Sql("/sql/userRepositoryTestData.sql")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void UserRepository_연결_확인(){
        //given
        User user = new User();
        user.setUsername("bin0017");
        user.setPassword("Bin@12345");
        user.setNickName("nickname");
        user.setRole(UserRoleEnum.USER);

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result).isNotNull();

    }

    @Test
    void UserRepository_findByUsername_User_객체_반환_동작_확인(){
        //given
        //when
        Optional<User> result = userRepository.findByUsername("bin0016");

        //then
        assertThat(result.isPresent()).isTrue();

    }


    @Test
    void UserRepository_findByUsername_User_객체가_없으면_Optional_empty_반환_동작_확인(){
        //given
        //when

        Optional<User> result = userRepository.findByUsername("bin00221");

        //then
        assertThat(result.isEmpty()).isTrue();

    }
}
