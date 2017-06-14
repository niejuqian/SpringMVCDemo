package com.example.controller;

import com.example.annotation.ArgumentNotResolved;
import com.example.annotation.ReturnTypeNotResolved;
import com.example.constant.CodeConstant;
import com.example.entity.ResultBody;
import com.example.entity.UserInfo;
import com.example.handler.exception.AppException;
import com.example.service.PayService;
import com.example.service.UserInfoService;
import com.example.utils.JsonHelper;
import com.example.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/5/27.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired(required = false)
    UserInfoService userInfoService;

    //@Resource
    //@Autowired
    //@Qualifier("alipay")
    @Inject
    @Named("wechatPay")
    PayService payService;

    @RequestMapping("/login")
    public UserInfo login() throws Exception {
        logger.info("==========================登录");
        System.out.println("=========================登录111");
        payService.pay();
        logger.info("=============userInfoService=" + userInfoService);
        UserInfo userInfo = userInfoService.getUserInfo("15899773751",1);
        logger.info("=================用户信息：" + userInfo);
        UserInfo userInfo1 = userInfoService.getUserInfo("15899773751",0);
        logger.info("=================用户信息1：" + userInfo1);
        return userInfo;
    }

    /**
     * <p>
     *     请求参数直接封装成对象
     * </p>
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/getBaseInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getBaseInfo(UserInfo userInfo, @ArgumentNotResolved HttpServletResponse response){
        logger.info("==========================获取信息");
        logger.info("====参数信息userInfo：" + userInfo);
        ResultBody<String> resultBody = new ResultBody<>();
        resultBody.setData("login");
        resultBody.setMsg(CodeConstant.SUCCESS.getMsg());
        resultBody.setCode(CodeConstant.SUCCESS.getCode());
        //return JsonHelper.toJson(resultBody);
        ResponseUtils.write(response,JsonHelper.toJson(resultBody));
        return null;
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
