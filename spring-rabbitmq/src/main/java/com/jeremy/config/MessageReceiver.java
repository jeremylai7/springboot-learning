package com.jeremy.config;

import com.jeremy.util.DateUtil;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/4/16
 * @desc:
 **/
@Component
public class MessageReceiver {

    @RabbitListener(queuesToDeclare = @Queue("myQueue2"))
    public void process(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        System.out.println("【接收信息】" + message + " 当前时间" + time);
    }

    /**
     * 死信队列接收
     * @param message
     */
    @RabbitListener(queues = DelayQueueRabbitConfig.DLX_QUEUE)
    public void delayPrecss(String message) throws InterruptedException {
        System.out.println("【接收消息】" + message + " 当前时间" + DateUtil.dateFormat(new Date()));
        //接收后数据库查询，延迟五秒
        Thread.sleep(5000);
    }

    //@RabbitListener(queues = DelayQueueRabbitConfig.DIRECT_QUEUE)
    public void delayProcess(String message) {
        System.out.println("【接收消息】" + message + " 当前时间" + DateUtil.dateFormat(new Date()));
    }
}
