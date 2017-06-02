package com.example.aop;

import com.example.HelloIntellij;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/6/2.
 * 记录每个接口执行时间
 */
@Aspect
public class LogExecuteTime {
    private Logger logger = LoggerFactory.getLogger(LogExecuteTime.class);

    @Pointcut("execution(* com.example.controller.LoginController.login(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        logger.info("=========================开始执行登录接口");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/spring-beans.xml");
        HelloIntellij helloIntellij = (HelloIntellij) applicationContext.getBean("helloIntellij");
        helloIntellij.sayHi();
    }

    @After("pointcut()")
    public void after() {
        logger.info("=========================登录接口业务处理执行完毕");
    }

    /*@Around("pointcut()")
    public void round(ProceedingJoinPoint joinPoint) {
        try {
            logger.info("=========================开始执行登录接口111");
            long start = System.currentTimeMillis();
            Object obj = joinPoint.proceed();
            logger.info("=====================返回数据：" + obj);
            long end = System.currentTimeMillis();
            logger.info("===========执行登录接口耗时：" + (end - start) + " ms");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }*/
}
