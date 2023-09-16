package com.fanplayground.fanplayground.controller;


import com.fanplayground.fanplayground.dto.LoginRequestDto;
import com.fanplayground.fanplayground.dto.SignupRequestDto;
import com.fanplayground.fanplayground.dto.UserUpdateRequestDto;
import com.fanplayground.fanplayground.dto.UserUpdateResponseDto;
import com.fanplayground.fanplayground.entity.Message;
import com.fanplayground.fanplayground.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // ResponseEntity<Map>
    @PostMapping("/auth/signup")
    public ResponseEntity<Message> signup(@Valid @RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @DeleteMapping("/auth/escape")
    public ResponseEntity<Message> escape(){
        return userService.escape();
    }

    @PutMapping("/auth/update")
    public UserUpdateResponseDto update(@Valid @RequestBody UserUpdateRequestDto requestDto){
        return userService.update(requestDto);
    }
}
