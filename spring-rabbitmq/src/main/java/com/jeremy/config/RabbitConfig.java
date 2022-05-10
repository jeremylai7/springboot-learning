package com.jeremy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: laizc
 * @date: created in 2022/5/10
 * @desc: 配置 rabbitTemplate
 **/
@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("【correlationData】:" + correlationData);
                log.info("【ack】" + ack);
                log.info("【cause】" + cause);
                if (ack) {
                    log.info("【发送成功】");
                } else {
                    log.info("【发送失败】correlationData:" + correlationData + " cause:" + cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("【消息发送失败】");
                log.info("【message】" + message);
                log.info("【replyCode】" + replyCode);
            }
        });

        return rabbitTemplate;
    }

}
