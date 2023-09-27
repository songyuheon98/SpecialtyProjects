package com.fanplayground.fanplayground.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/slack")
    public String test() {
        log.error("test-error");
        return "test";
    }
}