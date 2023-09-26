package com.fanplayground.fanplayground.controller.viewController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementViewController {
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
}
