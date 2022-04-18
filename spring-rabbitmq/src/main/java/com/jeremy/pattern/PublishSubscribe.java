package com.jeremy.pattern;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-18
 * @desc: 发布订阅模式，一个交换机绑定多个队列，每个队列绑定有唯一的消费者
 */
@Configuration
@RestController
public class PublishSubscribe {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("PUBLISH_SUBSCRIBE_EXCHANGE");
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
		return BindingBuilder.bind(psFirstQueue()).to(fanoutExchange());
	}

	@Bean
	public Binding routingSecondBinding() {
		return BindingBuilder.bind(psSecondQueue()).to(fanoutExchange());
	}

	@Bean
	public Binding routingThirdBinding() {
		return BindingBuilder.bind(psThirdQueue()).to(fanoutExchange());
	}

	/**
	 * 消息接收
	 */
	@RabbitListener(queues = "psFirstQueue")
	public void pubsubQueueFirst(String message) {
		System.out.println("【first】:" + message);
	}

	@RabbitListener(queues = "psSecondQueue")
	public void pubsubQueueSecond(String message) {
		System.out.println("【second】:" + message);
	}

	@RabbitListener(queues = "psThirdQueue")
	public void pubsubQueueThird(String message) {
		System.out.println("【third】:" + message);
	}

	/**
	 * 发送消息
	 */
	@GetMapping("/publish-sub-send")
	public String publishSubSend() {
		rabbitTemplate.convertAndSend("PUBLISH_SUBSCRIBE_EXCHANGE", null, "publish/subscribe hello");
		return "ok";
	}


}
