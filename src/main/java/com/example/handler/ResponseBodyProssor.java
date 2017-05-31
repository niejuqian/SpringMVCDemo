package com.example.handler;

import com.example.annotation.ReturnTypeNotResolved;
import com.example.constant.CodeConstant;
import com.example.constant.Constant;
import com.example.entity.ResultBody;
import com.example.utils.JsonHelper;
import com.example.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 * 统一响应处理
 */
public class ResponseBodyProssor extends RequestResponseBodyMethodProcessor{
    private Logger logger = LoggerFactory.getLogger(ResponseBodyProssor.class);

    public ResponseBodyProssor() {
        super(new ArrayList<>(Arrays.asList(new MappingJackson2HttpMessageConverter())));
        logger.info("================无参构造1");
    }

    public ResponseBodyProssor(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    public ResponseBodyProssor(List<HttpMessageConverter<?>> messageConverters, ContentNegotiationManager contentNegotiationManager) {
        super(messageConverters, contentNegotiationManager);
    }

    public ResponseBodyProssor(List<HttpMessageConverter<?>> messageConverters, ContentNegotiationManager contentNegotiationManager, List<Object> responseBodyAdvice) {
        super(messageConverters, contentNegotiationManager, responseBodyAdvice);
    }
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        logger.info("==========================supportsReturnType");
        /**
         * 将标注了ReturnTypeNotResolved注解的返回值、返回方法过滤掉
         * 如果返回false，将不会执行handleReturnValue函数
         */
        ReturnTypeNotResolved typeNotResolved = returnType.getMethodAnnotation(ReturnTypeNotResolved.class);
        if (null != typeNotResolved) {
            return false;
        }
        typeNotResolved = returnType.getParameterAnnotation(ReturnTypeNotResolved.class);
        if (null != typeNotResolved){
            return false;
        }
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        logger.info("==========================handleReturnValue");
        /**
         * 对Controller返回业务数据进行统一封装处理。
         * 能返回到这里的，说明业务处理没有问题，所以，返回code跟msg为成功
         * 将业务数据returnVlaue赋值给data
         */
        //响应数据统一处理
        ResultBody<Object> resultBody = new ResultBody<Object>();
        resultBody.setCode(CodeConstant.SUCCESS.getCode());
        resultBody.setMsg(CodeConstant.SUCCESS.getMsg());
        resultBody.setData(returnValue);
        String resultJson = JsonHelper.toJson(resultBody);
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        Object startStr = request.getSession().getAttribute(Constant.START_TIME_KEY);
        long startTime = null != startStr ? Long.valueOf(startStr + "") : 0;
        logger.info("请求URL：" + request.getRequestURI());
        logger.info("请求耗时： " + (System.currentTimeMillis() - startTime) + "毫秒");
        logger.info("响应数据：" + resultJson);
        //输出数据
        ResponseUtils.write(((HttpServletResponse)webRequest.getNativeResponse()), resultJson);
    }
}
