package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
public class TestScopeController {
    @RequestMapping("/test/mav")
    public ModelAndView testMAV() {
        ModelAndView mav = new ModelAndView();
        /**
         *ModelAndView包含Model和View的功能
         *Model：向请求域中共享数据
         *View：设置逻辑视图实现页面跳转
         */
        mav.addObject("testRequestScope","hello,ModelAndView");
        mav.setViewName("success");
        return mav;
    }

    @RequestMapping("/test/model")
    public String testModel(Model model) {

        model.addAttribute("testRequestScope", "Hello Model");

        // 设置逻辑视图
        return "success";
    }

    @RequestMapping("/test/modelMap")
    public String testNodelMap(ModelMap modelMap) {
        modelMap.addAttribute("testRequestScope","Hello,ModelMap");
        return "success";
    }

    @RequestMapping("/test/session")
    public String testSession(HttpSession session) {
        session.setAttribute("testSessionScope", "hello, session");
        return "success";
    }

    @RequestMapping("/test/application")
    public String testApplication(HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("testApplicationScope", "hello, application");
        return "success";
    }
}
