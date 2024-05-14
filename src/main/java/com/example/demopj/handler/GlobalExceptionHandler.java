package com.example.demopj.handler;

import com.example.demopj.type.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        boolean flag = false;
        if (e.getMessage().length() > 0)
                flag = true;
        return Result.error(flag?e.getMessage():"操作失败");
    }

//    Exception Handler be used in Model And View .
//    @ExceptionHandler(Exception.class)
//    public ModelAndView ModelAndViewHandlerException(Exception e){
//        e.printStackTrace();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }
}
