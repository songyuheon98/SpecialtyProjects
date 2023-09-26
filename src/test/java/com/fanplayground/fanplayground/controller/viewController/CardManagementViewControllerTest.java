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
class CardManagementViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void updateCard() throws Exception {
        mockMvc.perform(get("/card/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/card/update"))
                .andDo(print());
    }
    @Test
    void deleteCard() throws Exception {
        mockMvc.perform(get("/card/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("/card/delete"))
                .andDo(print());
    }
    @Test
    void createCard() throws Exception {
        mockMvc.perform(get("/card/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/card/create"))
                .andDo(print());
    }
}