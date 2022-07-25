package com.jeremy.config;

import com.jeremy.util.DateUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    @RabbitListener(queuesToDeclare = @Queue("myQueue33"))
    public void process3(String msg, Channel channel, Message message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        System.out.println("【接收信息】" + msg + " 当前时间" + time);
        System.out.println(message.getMessageProperties().getDeliveryTag());

        try {
            long tag = message.getMessageProperties().getDeliveryTag();
            if (tag >= 4) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * 死信队列接收
     * @param message
     */
    @RabbitListener(queues = DelayQueueRabbitConfig.DLX_QUEUE)
    public void delayPrecss(String msg,Channel channel,Message message) throws InterruptedException {
        System.out.println("【接收消息】" + msg + " 当前时间" + DateUtil.dateFormat(new Date()));
        //接收后数据库查询，延迟五秒
        //Thread.sleep(5000);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = XDelayedMessageConfig.DIRECT_QUEUE)
    public void delayProcess(String msg,Channel channel, Message message) {
        System.out.println("【接收消息】" + msg + " 当前时间" + DateUtil.dateFormat(new Date()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
