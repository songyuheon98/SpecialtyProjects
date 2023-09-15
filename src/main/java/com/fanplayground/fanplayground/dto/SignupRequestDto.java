package com.fanplayground.fanplayground.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    //@Pattern(regexp = "^[a-z][a-z0-9]{4,10}+$")
    private String username;

    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}")
    private String password;


    private String nickname;

    private String email;

    private boolean admin = false;
    private String adminToken ="";

}
