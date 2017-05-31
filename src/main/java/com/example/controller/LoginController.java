package com.example.controller;

import com.example.annotation.ReturnTypeNotResolved;
import com.example.constant.CodeConstant;
import com.example.entity.UserInfo;
import com.example.handler.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/27.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public UserInfo login(){
        logger.info("==========================登录");
        System.out.println("=========================登录111");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("王二小");
        userInfo.setAge(18);
        userInfo.setBalance(new BigDecimal("188.58"));
        userInfo.setBirthday(new Date());
        return userInfo;
    }

    /**
     * <p>
     *     请求参数直接封装成对象
     * </p>
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/getBaseInfo",method = RequestMethod.POST)
    public String getBaseInfo(UserInfo userInfo){
        logger.info("==========================获取信息");
        logger.info("====参数信息userInfo：" + userInfo);
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        logger.info("==========================注册");
        if (true) {
            throw new AppException(CodeConstant.SYS_ERROR);
        }
        return "login";
    }

    /**
     * 跳转到登录页面
     * <p>
     *     在方法上面标注[ReturnTypeNotResolved]注解，说明不使用公共的响应处理
     * </p>
     * @return
     */
    @RequestMapping("/login.html")
    @ReturnTypeNotResolved
    public String userLogin(){
        logger.info("==========================进入登录页面");
        return "login";
    }
}
