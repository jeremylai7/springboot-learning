package com.jeremy.pattern;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-18
 * @desc: 发布订阅模式，一个交换机绑定多个队列，每个队列绑定有唯一的消费者
 */
@RestController
public class PublishSubscribe {

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("PUBLISH_SUBSCRIBE_EXCHANGE");
	}

	@Bean
	public Queue psFirstQueue() {
		return new Queue("psFirstQueue");
	}

	@Bean
	public Queue psSecondQueue() {
		return new Queue("psSecondQueue");
	}

	@Bean
	public Queue psThirdQueue() {
		return new Queue("psThirdQueue");
	}

	@Bean
	public Binding routingFirstBinding() {
		return BindingBuilder.bind(psFirstQueue()).to(directExchange()).with("psFirstRoutingKey");
	}

	@Bean
	public Binding routingSecondBinding() {
		return BindingBuilder.bind(psSecondQueue()).to(directExchange()).with("psSecondRoutingKey");
	}

	@Bean
	public Binding routingThirdBinding() {
		return BindingBuilder.bind(psThirdQueue()).to(directExchange()).with("psThirdRoutingKey");
	}
}
