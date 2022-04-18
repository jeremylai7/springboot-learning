package com.jeremy.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-18
 * @desc: 延迟队列
 */
@Component
public class XDelayedMessageConfig {
	/**
	 * 队列
	 */
	public static final String DIRECT_QUEUE = "queue.delayed";

	/**
	 * 延迟交换机
	 */
	public static final String DELAYED_EXCHANGE = "exchange.delayed";

	/**
	 * 绑定的routing key
	 */
	public static final String ROUTING_KEY = "routingKey.bind";

	@Bean
	public Queue directQueue() {
		return QueueBuilder.durable(DIRECT_QUEUE).build();
	}

	/**
	 * 定义延迟交换机
	 * 交换机的类型为 x-delayed-message
	 * @return
	 */
	@Bean
	public Exchange delayedExchange() {
		Map<String,Object> map = new HashMap<>();
		map.put("x-delayed-type","direct");
		return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
	}

	@Bean
	public Binding delayOrderBinding() {
		return new Binding(DIRECT_QUEUE,Binding.DestinationType.QUEUE,DELAYED_EXCHANGE,ROUTING_KEY,null);
	}

}
