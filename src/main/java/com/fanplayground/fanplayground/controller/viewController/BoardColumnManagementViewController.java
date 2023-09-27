package com.fanplayground.fanplayground.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardColumnManagementViewController {

    @GetMapping("/user/column/create")
    public String createColumn() {
        return "/column/columnCreate";
    }

    @GetMapping("/user/column/update")
    public String updateColumn() {
        return "/column/columnUpdate";
    }

    @GetMapping("/user/column/delete")
    public String deleteColumn() {
        return "/column/columnDelete";
    }


}
