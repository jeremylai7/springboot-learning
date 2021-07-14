package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: laizc
 * @Date: Created in  2020-09-17
 * @desc: websocket配置
 */
@Configuration
public class WebSocketConfig {
    //tomcat启动无需该配置
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
