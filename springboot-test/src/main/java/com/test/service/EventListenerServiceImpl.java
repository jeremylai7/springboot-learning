package com.test.service;

import com.test.dto.DemoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2024/12/4
 * @desc:
 **/
@Service
@Slf4j
public class EventListenerServiceImpl implements EventListenerService{

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void add() {
        int a = 1 + 1;
        System.out.println(a);
        DemoEvent event = new DemoEvent(this);
        event.setName("hello");
        applicationEventPublisher.publishEvent(event);
        log.info("执行 service");
    }



}
