package com.jeremy.pattern;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
 * @desc: 路由模式
 **/
@Configuration
@RestController
public class Routing {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue routingFirstQueue() {
        return new Queue("routingFirstQueue");
    }

    @Bean
    public Queue routingSecondQueue() {
        return new Queue("routingSecondQueue");
    }

    @Bean
    public Queue routingThirdQueue() {
        return new Queue("routingThirdQueue");
    }

    @Bean
    public DirectExchange routingExchange() {
        return new DirectExchange("routingExchange");
    }

    @Bean
    public Binding routingFirstBind() {
        return BindingBuilder.bind(routingFirstQueue()).to(routingExchange()).with("firstRouting");
    }

    @Bean
    public Binding routingSecondBind() {
        return BindingBuilder.bind(routingSecondQueue()).to(routingExchange()).with("secondRouting");
    }

    @Bean
    public Binding routingThirdBind() {
        return BindingBuilder.bind(routingThirdQueue()).to(routingExchange()).with("thirdRouting");
    }

    /**
     * 接收消息
     */

    @RabbitListener(queues = "routingFirstQueue")
    public void routingFirstListener(String message) {
        System.out.println("【routing first】" + message);
    }

    @RabbitListener(queues = "routingSecondQueue")
    public void routingSecondListener(String message) {
        System.out.println("【routing second】" + message);
    }

    @RabbitListener(queues = "routingThirdQueue")
    public void routingThirdListener(String message) {
        System.out.println("【routing third】" + message);
    }


    @GetMapping("/routing-first")
    public String routingFirst() {
        // 使用不同的routingKey 转发到不同的队列
        rabbitTemplate.convertAndSend("routingExchange","firstRouting"," first routing message");
        rabbitTemplate.convertAndSend("routingExchange","secondRouting"," second routing message");
        rabbitTemplate.convertAndSend("routingExchange","thirdRouting"," third routing message");
        return "ok";
    }


}
