package com.fanplayground.fanplayground.controller.viewController;
import org.springframework.web.bind.annotation.GetMapping;

public class CardManagementViewController {
    @GetMapping("/card/update")
    public String updateCard() {
        return "/card/update";
    }
    @GetMapping("/card/delete")
    public String deleteCard() {
        return "/card/delete";
    }
    @GetMapping("/card/create")
    public String createCard() {
        return "/card/create";
    }
}
