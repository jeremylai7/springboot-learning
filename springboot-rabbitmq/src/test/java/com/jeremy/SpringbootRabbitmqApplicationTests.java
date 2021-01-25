package com.jeremy;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringbootRabbitmqApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
		rabbitTemplate.convertAndSend("myQueuess", " this is message");
		//发送方确认机制
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
