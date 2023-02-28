package com.test.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: laizc
 * @date: created in 2022/9/18
 * @desc:
 **/
@Aspect
@Component
public class AopTestAspect {


    @Pointcut("execution(public * com.test.controller.Aop*.*(..))" +
    " && !execution(public * com.test.controller.AopSecondController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void before() {
        System.out.println("okok");
    }

}
