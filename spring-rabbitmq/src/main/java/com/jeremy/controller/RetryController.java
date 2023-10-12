package com.jeremy.controller;

import com.jeremy.config.XDelayedMessageConfig;
import com.jeremy.util.DateUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: laizc
 * @date: created in 2023/10/12
 * @desc: 模拟微信
 **/
@RestController
public class RetryController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final int[] INTERVAL_ARRAY= {5,10,30};

    @GetMapping("/retry")
    public String retry(int index) {
        if (index >= 0 && index <= 2) {
            send(index +",延迟" + INTERVAL_ARRAY[index] + "s",INTERVAL_ARRAY[index]);
        }
        return "ok";
    }


    private void send(String message,Integer delayTime) {
        message = message + " " + DateUtil.dateFormat(new Date());
        System.out.println("【发送消息】" + message);
        rabbitTemplate.convertAndSend(XDelayedMessageConfig.DELAYED_EXCHANGE,XDelayedMessageConfig.RETRY_ROUTING_KEY,
                message, message1 -> {
                    message1.getMessageProperties().setDelay(delayTime*1000);
                    return message1;
                });
    }

    @RabbitListener(queues = XDelayedMessageConfig.RETRY_QUEUE)
    public void delayProcess(String msg, Channel channel, Message message) {
        System.out.println("【接收消息】" + msg + " 当前时间" + DateUtil.dateFormat(new Date()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = Integer.parseInt(msg.split(",")[0]);
        retry(++index);
    }

    public static void main(String[] args) {
        String aa = "延迟10s,20:37:07".split(",")[0];
        Pattern pattern = Pattern.compile("\\d+");  // \d 表示数字，+ 表示一个或多个
        Matcher matcher = pattern.matcher(aa);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
