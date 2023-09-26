package com.fanplayground.fanplayground.controller.viewController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class BoardManagementViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void signupPage() throws Exception {
        mockMvc.perform(get("/user/board"))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/boardCreate"))
                .andDo(print());
    }

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/user/board/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/boardUpdate"))
                .andDo(print());
    }

    @Test
    void updatePage() throws Exception {
        mockMvc.perform(get("/user/board/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/boardDelete"))
                .andDo(print());
    }
}