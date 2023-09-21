package com.fanplayground.fanplayground;

import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
@Commit
class MysqlTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_연결_확인(){
        //given
        User user = new User();
        user.setId(23L);
        user.setUsername("bin00177");
        user.setPassword("Bin@123457");
        user.setNickName("nickname7");
        user.setRole(UserRoleEnum.USER);

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result).isNotNull();


    }



}
