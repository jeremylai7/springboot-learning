package com.jeremy.controller;

import com.jeremy.threadpool.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2023/10/31
 * @desc:
 **/
@RestController
public class ThreadController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/thread")
    public String thread() {
        asyncService.executeAsync();
        return "ok";
    }
}
