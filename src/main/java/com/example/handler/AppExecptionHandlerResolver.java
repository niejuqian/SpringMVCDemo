package com.example.handler;

import com.example.constant.CodeConstant;
import com.example.entity.ResultBody;
import com.example.handler.exception.AppException;
import com.example.utils.JsonHelper;
import com.example.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/5/27.
 * 全局异常处理
 */
public class AppExecptionHandlerResolver implements HandlerExceptionResolver{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.info("=============================resolveException");
        if (null != e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        ResultBody responseBody = new ResultBody();
        if (null != e && e instanceof AppException) {
            responseBody.setCode(((AppException)e).getCode());
            responseBody.setMsg(e.getMessage());
        } else {
            responseBody.setCode(CodeConstant.SYS_ERROR.getCode());
            responseBody.setMsg(CodeConstant.SYS_ERROR.getMsg());
        }
        ResponseUtils.write(httpServletResponse, JsonHelper.toJson(responseBody));
        return new ModelAndView();
    }
}
