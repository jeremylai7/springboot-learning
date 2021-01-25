package com.jeremy;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootRabbitmqApplicationTests {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Test
	public void contextLoads() {
		for (int i = 0; i < 10; i++) {
			amqpTemplate.convertAndSend("myQueuess",i + " this is message");
		}
	}

	/**
	 * 根据rootkey分发到不同的队列
	 */
	@Test
	public void orderContext() {
		amqpTemplate.convertSendAndReceive("orderExchange","computer","this is comput message");
	}

	@Test
	public void fruitContext() {
		amqpTemplate.convertAndSend("orderExchange","fruit","this is fruit message");
	}

}
