package com.fanplayground.fanplayground.controller.viewController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class BoardColumnManagementViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void createColumn() throws Exception {
        mockMvc.perform(get("/user/column/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/column/columnCreate"))
                .andDo(print());
    }

    @Test
    void updateColumn() throws Exception {
        mockMvc.perform(get("/user/column/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/column/columnUpdate"))
                .andDo(print());
    }

    @Test
    void deleteColumn() throws Exception {
        mockMvc.perform(get("/user/column/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("/column/columnDelete"))
                .andDo(print());
    }
}