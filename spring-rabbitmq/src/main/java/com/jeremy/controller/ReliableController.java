package com.jeremy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: laizc
 * @date: created in 2022/5/10
 * @desc: 测试消息的可靠性传输
 **/
@RestController
@RequestMapping("/reliable")
@Slf4j
public class ReliableController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String send(String message) {
        rabbitTemplate.convertAndSend("myExchange","myRoutingKey",message,new CorrelationData(UUID.randomUUID().toString()));
	    System.out.println("【发送消息】" + message);
        return "【send message】" + message;
    }






}
