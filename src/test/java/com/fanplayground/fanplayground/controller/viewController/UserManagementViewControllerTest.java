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
class UserManagementViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void signupPage() throws Exception {
        mockMvc.perform(get("/user/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/signup"))
                .andDo(print());
    }

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/login"))
                .andDo(print());
    }

    @Test
    void updatePage() throws Exception {
        mockMvc.perform(get("/user/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/update"))
                .andDo(print());
    }

    @Test
    void escapePage() throws Exception {
        mockMvc.perform(get("/user/escape"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/escape"))
                .andDo(print());
    }
}