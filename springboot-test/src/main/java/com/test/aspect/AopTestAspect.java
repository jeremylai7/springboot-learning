package com.test.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: laizc
 * @date: created in 2022/9/18
 * @desc:
 **/
@Aspect
@Component
@Slf4j
public class AopTestAspect {


    /**
     * 定义切面，用来确定在哪些方法执行通知，实际内部不会执行。配置 before after Around 注解通知
     */
    @Pointcut("!execution(public * com.test.controller.Aop*.*(..))" +
    " && execution(public * com.test.controller.AopSecondController.*(..))")
    public void verify(){
        System.out.println("hello");
    }

    @Before("verify()")
    public void before() {
        System.out.println("before");
    }

    @After("verify()")
    public void after() {
        System.out.println("after");
    }

    @Around("verify()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        Object result = joinPoint.proceed(); // 执行目标方法
        return result;
    }

    @Around("@annotation(com.test.annotation.AopTest)")
    public Object annotationTest(ProceedingJoinPoint joinPoint) throws Throwable {log.info("执行前");
        log.info("执行前");
        Object result = joinPoint.proceed(); // 执行目标方法
        try {
            Object[] args = joinPoint.getArgs();
            // 添加日志
            log.info("执行后");
            int a = 1/0;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

}
