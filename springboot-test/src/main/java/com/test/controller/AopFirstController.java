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
@RequestMapping("/aop-first")
public class AopFirstController {

    @GetMapping
    @AopTest
    public void first() {
        System.out.println("first");
    }


}
