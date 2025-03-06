package com.test.controller;

import com.test.annotation.AopTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2022/9/18
 * @desc:
 **/
@RestController()
@RequestMapping("/aop-first")
@Slf4j
public class AopFirstController {

    @GetMapping
    @AopTest
    public String first(String param) {
        log.info("执行first方法");
        return "result " + param;
    }


}
