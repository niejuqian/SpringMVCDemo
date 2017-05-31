package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.annotation.ArgumentNotResolved;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/31.
 */
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {
    private Logger logger = LoggerFactory.getLogger(MethodArgumentResolver.class);
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        ArgumentNotResolved argumentNotResolved = methodParameter.getParameterAnnotation(ArgumentNotResolved.class);
        if (null != argumentNotResolved) return false;
        argumentNotResolved = methodParameter.getMethodAnnotation(ArgumentNotResolved.class);
        if (null != argumentNotResolved) return false;
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        logger.info("=======================resolveArgument");
        Object arg = null;
        Class<?> parameterType = methodParameter.getParameterType();
        //数据转换
        arg = converterArgument(parameterType,arg,nativeWebRequest);
        //参数校验
        if (null != arg) {
            validParams(arg);
        }
        return arg;
    }

    /**
     * 数据转换
     * @param parameterType
     * @param arg
     * @param webRequest
     * @return
     * @throws IOException
     */
    private Object converterArgument(Class<?> parameterType,Object arg,NativeWebRequest webRequest) throws IOException {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        ServletInputStream inputStream = request.getInputStream();
        String method = request.getMethod();
        if (RequestMethod.POST.name().equals(method)) {
            //Post提交方式
            String content = IOUtils.toString(inputStream,"UTF-8");
            arg = JSON.parseObject(content,parameterType);
        } else if (RequestMethod.GET.name().equals(method)) {
            //GET提交方式

        }
        return arg;
    }

    private void validParams(Object arg) {

    }
}
