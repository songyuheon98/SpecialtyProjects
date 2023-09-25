package com.fanplayground.fanplayground.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        return "/user/update";
    }

    @GetMapping("/user/escape")
    public String escapePage() {
        return "/user/escape";
    }

    @GetMapping("/user/logout")
    public String logout() {
        /**
         * 쿠키 삭제 기능 추가
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "/user/login";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "/main";
    }


}
