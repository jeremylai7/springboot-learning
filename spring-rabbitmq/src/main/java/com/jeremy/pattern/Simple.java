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
 * @desc: 简单模式
 * 简答模式就是 生产者向队列发送消息，队列再发送到消费者
 */
@RestController
public class Simple {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/simple-send")
	public String simpleSend() {
		rabbitTemplate.convertAndSend("simple","this is news");
		return "ok";
	}

	@RabbitListener(queuesToDeclare = @Queue("simple"))
	public void consume(String message) {
		System.out.println(message);
	}



}
