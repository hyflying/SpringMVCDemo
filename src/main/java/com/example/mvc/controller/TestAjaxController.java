package com.example.mvc.controller;

import com.example.mvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestAjaxController {
    @RequestMapping("/test/ajax")
    public void testAjax(Integer id,@RequestBody String requestBody, HttpServletResponse response) throws IOException {
        System.out.println("RequestBody:"+requestBody);
        System.out.println("id:"+id);
        response.getWriter().write("Hello,axios");
    }

    @RequestMapping("test/RequestBody/json")
    public void testRequestBody(@RequestBody User user, HttpServletResponse response) throws IOException {
        System.out.println(user);
        response.getWriter().write("hello,RequestBody");
    }

    @RequestMapping("test/ResponseBody")
    @ResponseBody
    public String testResponseBody() {
        return "success";
    }
    @RequestMapping("/test/ResponseBody/json")
    @ResponseBody
    public User testResponseBodyJSON() {
        User user = new User(1, "evan", "332211", 23, "男");
        return user;
    }
}
