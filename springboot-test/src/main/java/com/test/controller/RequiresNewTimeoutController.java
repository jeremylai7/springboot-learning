package com.test.controller;

import com.test.service.RequiresNewTimeoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: 2026/10/12
 * @desc:   requires-new 传播机制，导致超时问题
 */
@RestController
@RequestMapping("/requires-new-timeout")
public class RequiresNewTimeoutController {

    @Autowired
    private RequiresNewTimeoutService requiresNewTimeoutService;

    @GetMapping("/test")
    public void test(){
        requiresNewTimeoutService.orderPass();

    }



}
