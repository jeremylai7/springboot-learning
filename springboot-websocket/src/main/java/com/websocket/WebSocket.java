package com.websocket;

import com.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
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

	@OnOpen
	public void onOpen(Session session) throws SocketException {
		this.session = session;
		webSocketSet.put(this.session.getId(),this);
		String ip = IpUtils.getServerIp() + "_" + IpUtils.getPort();
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
			sendMessage(message);
		}
	}

	/**
	 * 发送消息
	 * @param message
	 * @return 全部都发送一遍
	 */
	public void sendMessage(String message){
		for (WebSocket webSocket : webSocketSet.values()) {
			webSocket.session.getAsyncRemote().sendText(message);
		}
		log.info("【wesocket】广播消息,message={}", message);

	}

}
