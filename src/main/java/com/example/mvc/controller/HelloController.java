package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    //设置首页页面
//    @RequestMapping("/")
//    public String portal() {
//        return "index";
//    }
    @RequestMapping("/hello")
    public String hello() {
        return "success";
    }
    @RequestMapping("/testrest/{username}/{id}")
    public String testrest(@PathVariable("username") String username, @PathVariable("id") String id) {
        return "success";
    }
}
