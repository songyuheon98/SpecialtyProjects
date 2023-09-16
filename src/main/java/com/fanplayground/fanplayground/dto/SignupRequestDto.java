package com.fanplayground.fanplayground.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    //@Pattern(regexp = "^[a-z][a-z0-9]{4,10}+$")
    private String username;

    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,15}+$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password1;

    private String password2;


    private String nickname;


    private boolean admin = false;
    private String adminToken ="";

}
