package com.threadpool.controller;

import com.threadpool.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: created in 2022-06-15
 *
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    public String index() {
        indexService.sendMessage("发送消息");
        return "send ok";
    }


}
