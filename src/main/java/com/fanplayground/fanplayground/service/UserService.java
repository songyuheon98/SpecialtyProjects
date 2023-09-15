package com.fanplayground.fanplayground.service;


import com.fanplayground.fanplayground.dto.SignupRequestDto;
import com.fanplayground.fanplayground.entity.Message;
import com.fanplayground.fanplayground.entity.User;
import com.fanplayground.fanplayground.entity.UserRoleEnum;
import com.fanplayground.fanplayground.exception.DuplicateUsernameException;
import com.fanplayground.fanplayground.jwt.SecurityUtil;
import com.fanplayground.fanplayground.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<Message> signup(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new DuplicateUsernameException("중복된 username 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role,nickname);
        userRepository.save(user);

        Message msg = new Message("회원가입 성공",200);
        return new ResponseEntity<>(msg, null, HttpStatus.OK);
    }
    public ResponseEntity<Message> escape(){

        String username = SecurityUtil.getPrincipal().get().getUsername();

        userRepository.delete(userRepository.findByUsername(username).orElse(null));
        Message msg = new Message("회원 삭제 성공",200);

        // 해당 사용자(username)가 작성한 게시글인지 확인
        // setSubject(username)
        //post 삭제
        return new ResponseEntity<>(msg, null, HttpStatus.OK);
    }
}
