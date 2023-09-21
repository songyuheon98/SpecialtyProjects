package com.fanplayground.fanplayground.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestDto {

    //@Pattern(regexp = "^[a-z][a-z0-9]{8,15}+$")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{4,10}", message = "아이디 4~10자 영문 소문자, 숫자를 사용하세요.")
    private String username;

    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,15}+$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password1;

    private String password2;

    //@Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$.{5,20}")
    private String nickName;

    @Builder.Default
    private boolean admin = false;
    @Builder.Default
    private String adminToken ="";

}
