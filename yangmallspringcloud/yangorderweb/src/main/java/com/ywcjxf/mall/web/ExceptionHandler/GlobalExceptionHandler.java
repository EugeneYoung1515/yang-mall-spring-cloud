package com.ywcjxf.mall.web.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        //System.out.println("打印异常信息：");
        //e.printStackTrace();

        logger.error("打印异常信息："+"_"+e.getMessage(),e);

        return e.getMessage();
    }

}
