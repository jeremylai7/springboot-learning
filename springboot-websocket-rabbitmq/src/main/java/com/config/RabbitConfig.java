package com.config;

import com.utils.IpUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.SocketException;

/**
 * @author: laizc
 * @date: created in 2022-11-08
 * @desc:
 */
@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("PUBLISH_SUBSCRIBE_EXCHANGE");
    }

    @Bean
    public Queue psQueue() throws SocketException {
        String ip = IpUtils.getServerIp() + "_" + IpUtils.getPort();
        return new Queue("ps_" + ip);
    }

    @Bean
    public Binding routingFirstBinding() throws SocketException {
        return BindingBuilder.bind(psQueue()).to(fanoutExchange());
    }
}
