package com.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2023/10/12
 * @desc: 17track 通知
 **/
@RestController
public class _17TrackController {

    @PostMapping("/web-hook")
    public String webHook(@RequestBody String body) {
        System.out.println(body);
        return "";
    }

}
