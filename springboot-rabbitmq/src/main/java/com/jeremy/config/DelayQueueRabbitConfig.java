package com.jeremy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-15
 * @desc: 延迟队列配置
 */
@Configuration
public class DelayQueueRabbitConfig {

	/**
	 * 死信队列
	 */
	public static final String DLX_QUEUE = "queue.dlx";

	/**
	 * 死信交换机
	 */
	public static final String DLX_EXCHANGE = "exchange.dlx";

	/**
	 * 死信routing-key
	 */
	public static final String DLX_ROUTING_KEY = "routingKey.dlx";

	/**
	 * 订单延迟队列
	 */
	public static final String ORDER_QUEUE = "queue.order";

	/**
	 * 订单交换机
	 */
	public static final String ORDER_EXCHANGE = "exchange.order";

	/**
	 * 订单routing-key
	 */
	public static final String ORDER_ROUTING_KEY = "routingkey.order";

	/**
	 * 死信队列
	 * @return
	 */
	@Bean
	public Queue dlxQueue() {
		return new Queue(DLX_QUEUE,true);
	}

	/**
	 * 死信交换机
	 * @return
	 */
	@Bean
	public DirectExchange dlxExchange() {
		return new DirectExchange(DLX_EXCHANGE,true,false);
	}

	/**
	 * 死信队列和交换机绑定
	 * @return
	 */
	@Bean
	public Binding bindingDLX() {
		return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
	}

	/**
	 * 订单延迟队列
	 * @return
	 */
	@Bean
	public Queue orderQueue() {
		Map<String,Object> params = new HashMap<>();
		params.put("x-dead-letter-exchange", DLX_EXCHANGE);
		params.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
		return new Queue(ORDER_QUEUE, true, false, false, params);
	}

	/**
	 * 订单交换机
	 * @return
	 */
	@Bean
	public DirectExchange orderExchange() {
		return new DirectExchange(ORDER_EXCHANGE,true,false);
	}

	/**
	 * 订单队列和交换机绑定
	 * @return
	 */
	@Bean
	public Binding orderBinding() {
		return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(DLX_ROUTING_KEY);
	}


}
