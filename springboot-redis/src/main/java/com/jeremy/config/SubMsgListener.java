package com.jeremy.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-21
 * @desc:
 */
@Component
public class SubMsgListener implements MessageListener{

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String channelName = new String(pattern);
		System.out.println(channelName);
		System.out.println("【接收消息】" + new String(message.getBody()));
	}
}
