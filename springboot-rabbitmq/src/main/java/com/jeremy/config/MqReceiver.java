package com.jeremy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: laizc
 * @Date: Created in  2021-01-25
 * @desc: 接收消息通知
 */
@Slf4j
@Component
public class MqReceiver {

	private static String EXCHANGE = "myExchange";

	private static String ORDER_EXCHANGE = "orderExchange";

	//1、需要手动创建队列
	//@RabbitListener(queues = "myQueue")
	//2、队列不存在自动创建队列
	//@RabbitListener(queuesToDeclare = @Queue("myQueues"))
	//3、自动创建, Exchange和Queue绑定
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue("myQueuess"),
			exchange = @Exchange("myExchange")
	))
	public void process(String message) {
		log.info("messsage: {}",message);
	}


	/**
	 * 根据不同的key分发到不同的队列
	 * @param message
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange("orderExchange"),
			key = "computer",
			value = @Queue("orderQueue")
	))
	public void computProcess(String message){
		log.info("this comput receiver msg: {}",message);
	}

	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange("orderExchange"),
			key = "fruit",
			value = @Queue("fruitQueue")
	))
	public void fruitProcess(String message) {
		log.info("this fruit receiver msg: {}",message);
	}

}
