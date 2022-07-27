package com.jeremy.pattern;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-19
 * @desc: rpc 模式，发送信息后会获取返回信息
 */
@Configuration
@RestController
public class Rpc {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(queuesToDeclare =@Queue("rpcQueue"))
	public String rpcListener(String message) {
		System.out.println("【rpc接收消息】" + message);
		return "rpc 返回" + message;
	}

	@GetMapping("/rpc-send")
	public void rpcSend() {
		Object receive = rabbitTemplate.convertSendAndReceive("rpcQueue","rpc send message");
		System.out.println("【发送端接收消息】" + receive);
	}



}
