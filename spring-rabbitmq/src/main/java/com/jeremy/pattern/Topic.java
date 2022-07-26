package com.jeremy.pattern;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2022/4/18
 * @desc: 通配符 routing key
 * # 一个或多个 * 一个
 **/
@Configuration
@RestController
public class Topic {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue topicFirstQueue() {
        return new Queue("topicFirstQueue");
    }

    @Bean
    public Queue topicSecondQueue() {
        return new Queue("topicSecondQueue");
    }

    @Bean
    public Queue topicThirdQueue() {
        return new Queue("topicThirdQueue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding topicFirstBind() {
        // .com 为结尾
        return BindingBuilder.bind(topicFirstQueue()).to(topicExchange()).with("*.com");
    }

    @Bean
    public Binding topicSecondBind() {
        return BindingBuilder.bind(topicSecondQueue()).to(topicExchange()).with("*.cn");
    }

    @Bean
    public Binding topicThirdBind() {
        return BindingBuilder.bind(topicThirdQueue()).to(topicExchange()).with("www.#");
    }

    /**
     * 接收消息
     */

    @RabbitListener(queues = "topicFirstQueue")
    public void topicFirstListener(String message) {
        System.out.println("【topic first】" + message);
    }

    @RabbitListener(queues = "topicSecondQueue")
    public void topicSecondListener(String message) {
        System.out.println("【topic second】" + message);
    }

    @RabbitListener(queues = "topicThirdQueue")
    public void topicThirdListener(String message) {
        System.out.println("【topic third】" + message);
    }

    @GetMapping("/topic-first-send")
    public String topicFirstSend(String message) {
        rabbitTemplate.convertAndSend("topicExchange",message,message);
        return "topic ok";
    }



}
