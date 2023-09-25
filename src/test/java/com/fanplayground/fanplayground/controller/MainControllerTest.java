//package com.fanplayground.fanplayground.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//class MainControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Test
//    void index() throws Exception {
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/main"))
//                .andDo(print());
//    }
//
//    @Test
//    void getBoard() throws Exception {
//        mockMvc.perform(get("/board/{boardId}", 1L ))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/board/boardview"))
//                .andDo(print());
//    }
//}