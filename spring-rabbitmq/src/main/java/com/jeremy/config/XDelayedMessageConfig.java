package com.jeremy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-18
 * @desc: 延迟队列
 */
@Configuration
public class XDelayedMessageConfig {

	/**
	 * 延迟交换机
	 */
	public static final String DELAYED_EXCHANGE = "exchange.delayed";

	/**
	 * 队列
	 */
	public static final String DIRECT_QUEUE = "queue.delayed";


	/**
	 * 绑定的routing key
	 */
	public static final String ROUTING_KEY = "routingKey.bind";

	/**
	 * 重试队列
	 */
	public static final String RETRY_QUEUE = "queue.retry";


	/**
	 * 重试routing key
	 */
	public static final String RETRY_ROUTING_KEY = "routingKey.bind.retry";



	@Bean
	public Queue directQueue() {
		return new Queue(DIRECT_QUEUE,true);
	}

	@Bean
	public Queue retryQueue() {
		return new Queue(RETRY_QUEUE,true);
	}




	/**
	 * 定义延迟交换机
	 * 交换机的类型为 x-delayed-message
	 * @return
	 */
	@Bean
	public CustomExchange delayedExchange() {
		Map<String,Object> map = new HashMap<>();
		map.put("x-delayed-type","direct");
		return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
	}

	@Bean
	public Binding delayOrderBinding() {
		return BindingBuilder.bind(directQueue()).to(delayedExchange()).with(ROUTING_KEY).noargs();
	}

	@Bean
	public Binding retryQueueBinding() {
		return BindingBuilder.bind(retryQueue()).to(delayedExchange()).with(RETRY_ROUTING_KEY).noargs();
	}



}
