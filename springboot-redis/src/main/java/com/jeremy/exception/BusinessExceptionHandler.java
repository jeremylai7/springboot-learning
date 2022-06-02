package com.jeremy.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import java.util.Set;


/**
 * @author Austen
 * @create 2018-04-04 9:03
 * @desc 业务异常处理器
 **/
@RestControllerAdvice
public class BusinessExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    /**
     * 业务异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public Result handle(BusinessException e) {
        Result result = new Result();
        result.setCode("-1");
        result.setMessage(e.getMessage());
        logger.error("business exception:" + result.getMessage() + "");
        return result;
    }
}

