package com.test.controller;

import com.test.service.EventListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2024/12/4
 * @desc: 事件监听测试
 **/
@RestController
@RequestMapping("/eventListener")
public class EventListenerController {

    @Autowired
    private EventListenerService eventListenerService;

    @GetMapping
    public void publish() {
        eventListenerService.publish();
    }

}
