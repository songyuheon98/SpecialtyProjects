package com.fanplayground.fanplayground.controller.viewController;
import org.springframework.web.bind.annotation.GetMapping;

public class BoardManagementViewController {
    @GetMapping("/user/board")
    public String createBoard() {
        return "/board/boardCreate";
    }
    @GetMapping("/user/board/update")
    public String updateBoard() {
        return "/board/boardUpdate";
    }

    @GetMapping("/user/board/delete")
    public String deleteBoard() {
        return "/board/boardDelete";
    }
}
