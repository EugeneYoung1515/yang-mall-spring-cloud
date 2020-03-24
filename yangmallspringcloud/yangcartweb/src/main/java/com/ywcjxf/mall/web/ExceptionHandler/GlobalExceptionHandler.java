package com.ywcjxf.mall.web.ExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException e) {
        System.out.println("打印异常信息：");
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("/fail");
        modelAndView.addObject("errorMsg",e);
        return modelAndView;
    }
    */

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String handleException(RuntimeException e) {
        System.out.println("打印异常信息：");
        if(e.getCause()!=null){
            e.getCause().printStackTrace();
        }
        e.printStackTrace();
        return e.getMessage();
    }

}
