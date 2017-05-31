package com.example.controller;

import com.example.constant.CodeConstant;
import com.example.handler.exception.AppSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/5/31.
 * 错误处理
 */
@Controller
@RequestMapping("/error")
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);


    @RequestMapping("/404")
    public String err404(){
        logger.info("================================404");
        throw new AppSystemException(CodeConstant.SYS_ERROR);
    }


    @RequestMapping("/502")
    public String err502(){
        logger.info("================================502");
        throw new AppSystemException(CodeConstant.SYS_ERROR);
    }
}
