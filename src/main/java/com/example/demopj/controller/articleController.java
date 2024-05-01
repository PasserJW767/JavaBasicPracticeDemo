package com.example.demopj.controller;

import com.example.demopj.Utils.JwtUtil;
import com.example.demopj.type.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class articleController {

    @GetMapping("/list")
    public Result<String> list(){
        return Result.success("所有文章数据");
    }

}
