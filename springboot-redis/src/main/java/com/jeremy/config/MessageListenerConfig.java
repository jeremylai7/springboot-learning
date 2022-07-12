package com.jeremy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-21
 * @desc: 发布订阅 消息监听器
 */
@Configuration
public class MessageListenerConfig {

	@Autowired
	private SubMsgListener subMsgListener;

	/**
	 * 消息监听容器
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		List<ChannelTopic> list = new ArrayList<>();
		ChannelTopic channelTopic = new ChannelTopic("runoobChat");
		ChannelTopic channelTopic2 = new ChannelTopic("runoobChat2");
		list.add(channelTopic);
		list.add(channelTopic2);
		container.addMessageListener(new MessageListenerAdapter(subMsgListener),list);
		return container;
	}
}
