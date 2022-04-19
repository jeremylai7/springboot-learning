package com.jeremy.pattern;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-19
 * @desc: Header模式
 * 根据 header 中的key/value 键值对来进行匹配
 * binding 配置了多少键值对，发送就要加多少，才能匹配到信息
 */
@Configuration
@RestController
public class Header {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean
	public Queue headerFirstQueue() {
		return new Queue("headerFirstQueue");
	}

	@Bean
	public Queue headerSecondQueue() {
		return new Queue("headerSecondQueue");
	}

	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange("headerExchange");
	}

	@Bean
	public Binding headerFirstBinding() {
		Map<String,Object> map = new HashMap<>();
		map.put("matchAll","YES");
		map.put("hello","good");
		return BindingBuilder.bind(headerFirstQueue()).to(headersExchange()).whereAny(map).match();
	}

	@Bean
	public Binding headerSecondBinding() {
		Map<String,Object> map = new HashMap<>();
		map.put("matchAll","No");
		map.put("hello","world");
		return BindingBuilder.bind(headerSecondQueue()).to(headersExchange()).whereAny(map).match();
	}

	@RabbitListener(queues = "headerFirstQueue")
	public void headerFirstListener(byte[] message) {
		System.out.println("【first header 接收】" + new String(message));
	}

	@RabbitListener(queues = "headerSecondQueue")
	public void headerSecondListener(byte[] message) {
		System.out.println("【second header 接收】" + new String(message));
	}

	@GetMapping("/header-first-send")
	public void headerFirstSend() throws UnsupportedEncodingException {
		MessageProperties properties = new MessageProperties();
		properties.setHeader("matchAll","YES");
		properties.setHeader("hello","good");
		Message message = new Message("first header send".getBytes("UTF-8"),properties);
		rabbitTemplate.convertAndSend("headerExchange",null,message);
	}

	@GetMapping("/header-second-send")
	public void headerSecondSend() throws UnsupportedEncodingException {
		MessageProperties properties = new MessageProperties();
		properties.setHeader("matchAll","NO");
		properties.setHeader("hello","world");
		properties.setHeader("aa","aa");
		Message message = new Message("second header send".getBytes("UTF-8"),properties);
		rabbitTemplate.convertAndSend("headerExchange",null,message);
	}


}
