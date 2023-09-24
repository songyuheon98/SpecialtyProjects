package com.fanplayground.fanplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserWebController {

    @GetMapping("/user/signup")
    public String signupPage() {
        return "/user/signup";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "/user/login";
    }

    @GetMapping("/user/update")
    public String updatePage() {
        return "/user/userUpdate";
    }

    @GetMapping("/user/escape")
    public String escapePage() {
        return "/user/escape";
    }

}
