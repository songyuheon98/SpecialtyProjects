package com.fanplayground.fanplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Thymeleaf를 사용한 Spring 웹서비스");
        return "main.html";
    }
}
