package com.jeremy.pattern;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-18
 * @desc: 工作模式，一个生产者，多个消费者竞争一个消息，类似轮询，分发
 */
@RestController
public class Work {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/work-send")
	public String simpleSend() {
		rabbitTemplate.convertAndSend("work","this is news");
		return "ok";
	}

	@RabbitListener(queuesToDeclare = @Queue("work"))
	public void consume(String message) {
		System.out.println("first:" + message);
	}

	@RabbitListener(queuesToDeclare = @Queue("work"))
	public void consumeSecond(String message) {
		System.out.println("second:" + message);
	}

}
