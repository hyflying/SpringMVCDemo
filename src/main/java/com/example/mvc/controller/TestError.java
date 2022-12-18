package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class TestError {
    @RequestMapping("/test/error")
    public String tesError() {
        Integer i = 1/0;
        return "success";
    }
}
