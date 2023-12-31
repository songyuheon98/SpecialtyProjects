package com.fanplayground.fanplayground.controller.serviceController;

import com.fanplayground.fanplayground.dto.message.MessageDto;
import com.fanplayground.fanplayground.dto.user.signup.SignupRequestDto;
import com.fanplayground.fanplayground.dto.user.update.UserUpdateRequestDto;
import com.fanplayground.fanplayground.dto.user.update.UserUpdateResponseDto;
import com.fanplayground.fanplayground.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<MessageDto> signup(@Valid @RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @DeleteMapping("/escape")
    public ResponseEntity<MessageDto> escape(){
        ResponseEntity<MessageDto> escape_result= userService.escape();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();

        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return escape_result;

    }

    @PutMapping("/update")
    public UserUpdateResponseDto update(@Valid @RequestBody UserUpdateRequestDto requestDto){
        return userService.update(requestDto);
    }
}
