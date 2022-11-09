package com.websocket;

import com.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: laizc
 * @Date: Created in  2021-07-14
 * @desc:
 */
@Component
@ServerEndpoint(value = "/message")
@Slf4j
public class WebSocket {

	private static Map<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

	private Session session;

	private RabbitTemplate rabbitTemplate;

	@OnOpen
	public void onOpen(Session session) throws SocketException {
		this.session = session;
		webSocketSet.put(this.session.getId(),this);

		log.info("【websocket】有新的连接,总数:{}",webSocketSet.size());
	}

	@OnClose
	public void onClose(){
		String id = this.session.getId();
		if (id != null){
			webSocketSet.remove(id);
			log.info("【websocket】连接断开:总数:{}",webSocketSet.size());
		}
	}

	@OnMessage
	public void onMessage(String message){
		if (!message.equals("ping")){
			log.info("【wesocket】收到客户端发送的消息,message={}",message);
			//sendMessage(message);
			if (rabbitTemplate == null) {
				rabbitTemplate = (RabbitTemplate) SpringContextUtil.getBean("rabbitTemplate");
			}
			rabbitTemplate.convertAndSend("PUBLISH_SUBSCRIBE_EXCHANGE", null, message);
		}
	}

	/**
	 * 发送消息
	 * @param message
	 * @return
	 */
	public void sendMessage(String message){
		for (WebSocket webSocket : webSocketSet.values()) {
			webSocket.session.getAsyncRemote().sendText(message);
		}
		log.info("【wesocket】发送消息,message={}", message);

	}

	@RabbitListener(queues= "#{psQueue.name}")
	public void pubsubQueueFirst(String message) {
		sendMessage(message);
	}

}
