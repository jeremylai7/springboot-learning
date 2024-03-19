package com.test.controller;

import com.test.annotation.AopTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2022/9/18
 * @desc:
 **/
@RestController()
@RequestMapping("/aop-second")
public class AopSecondController {

    @GetMapping
    public void second() {
        System.out.println("second");
    }

    @GetMapping("/test")
    @AopTest
    public void test(String aa) {
        System.out.println("test annotation");
    }
}
