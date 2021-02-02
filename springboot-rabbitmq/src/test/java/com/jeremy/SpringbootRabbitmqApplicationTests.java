package com.jeremy;


import com.jeremy.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringbootRabbitmqApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void contextLoads() {



		CorrelationData correlation = new CorrelationData(UUID.randomUUID().toString());
		//测试找不到对应的exchange 消息确认机制反馈错误
		//rabbitTemplate.convertAndSend("myExchange","myQueuess", correlation.toString());
		rabbitTemplate.convertAndSend("myQueuess", correlation.toString());
		//发送方确认机制（publisher confirm）
		//消息发送到MQ那端之后，MQ会回一个确认收到的消息 Lambda 表达式
		rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
			log.info("CorrelationData content : " + correlationData);
			log.info("Ack status : " + ack);
			log.info("Cause content : " + s);
			if (ack){
				log.info("【发送成功】");
			}else {
				log.info("【发送失败】:{},消息异常:{}",correlationData,s);
			}
		});


		//设置消息返回 退回消息的所有信息 MQ接收失败或者路由失败
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->{
			log.info("被退回的消息为：{}", message);
			log.info("replyCode：{}", replyCode);
			log.info("replyText：{}", replyText);
			log.info("exchange：{}", exchange);
			log.info("routingKey：{}", routingKey);
		});

		//MQ突然宕机了或者被关闭了
		//解决：消息、队列、exhange持久化

	}

	/**
	 * 根据rootkey分发到不同的队列
	 */
	@Test
	public void orderContext() {
		rabbitTemplate.convertSendAndReceive("orderExchange","computer","this is comput message");

	}

	@Test
	public void fruitContext() {
		rabbitTemplate.convertAndSend("orderExchange","fruit","this is fruit message");
	}

}
