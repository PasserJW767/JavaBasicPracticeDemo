package com.example.demopj.aboutInterview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/userinfo")
    public ModelAndView info(){
        int i = 1 / 0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("username", "xxx");
        return modelAndView;
    }
}
