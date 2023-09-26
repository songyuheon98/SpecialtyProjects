package com.fanplayground.fanplayground.controller.viewController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardColumnManagementViewController {

    @GetMapping("/user/column/create")
    public String createColumn(Model model) {
        return "/column/columnCreate";
    }

    @GetMapping("/user/column/update")
    public String updateColumn(Model model) {
        return "/column/columnUpdate";
    }

    @GetMapping("/user/column/delete")
    public String deleteColumn(Model model) {
        return "/column/columnDelete";
    }

}
