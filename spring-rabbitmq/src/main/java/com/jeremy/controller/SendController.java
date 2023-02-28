package com.jeremy.controller;

import com.jeremy.config.DelayQueueRabbitConfig;
import com.jeremy.config.XDelayedMessageConfig;
import com.jeremy.util.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/4/16
 * @desc:
 **/
@RestController
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String send(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        rabbitTemplate.convertAndSend("myQueue2",message);
        System.out.println("【发送消息】" + message + " 当前时间" + time);
        return "send message " + message;
    }

    /**
     * 死信队列
     * @return
     */
    @GetMapping("/dlx")
    public String dlx() {
        dlxSend("延迟5 秒","5000");
        dlxSend("延迟2 秒","2000");
	    dlxSend("延迟10秒","10000");
	    return "ok";
    }

    private void dlxSend(String message,String delayTime) {
        message = message + " " + DateUtil.dateFormat(new Date());
        System.out.println("【发送消息】" + message);
        rabbitTemplate.convertAndSend(DelayQueueRabbitConfig.ORDER_EXCHANGE,DelayQueueRabbitConfig.ORDER_ROUTING_KEY,
                message, message1 -> {
                    message1.getMessageProperties().setExpiration(delayTime);
                    return message1;
                });
    }

    /**
     * 延迟队列
     * @return
     */
    @GetMapping("/delay")
    public String delay() {
	    delaySend("延迟队列10 秒",10000);
	    delaySend("延迟队列5 秒",5000);
	    delaySend("延迟队列2 秒",2000);
        return "ok";
    }

    private void delaySend(String message,Integer delayTime) {
        message = message + " " + DateUtil.dateFormat(new Date());
        System.out.println("【发送消息】" + message);
        rabbitTemplate.convertAndSend(XDelayedMessageConfig.DELAYED_EXCHANGE,XDelayedMessageConfig.ROUTING_KEY,
                message, message1 -> {
                    message1.getMessageProperties().setDelay(delayTime);
                    //message1.getMessageProperties().setHeader("x-delay",delayTime);
                    return message1;
                });
    }


}
