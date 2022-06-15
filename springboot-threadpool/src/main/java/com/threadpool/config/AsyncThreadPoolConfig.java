package com.threadpool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: created in 2022-06-15
 */
@Configuration
public class AsyncThreadPoolConfig {

    @Bean
    public Executor asyncThreadPool() {
        int corePoolSize = 1;
        int maximumPoolSize = 1;
        long keepAliveTime = 10;
        int queueSize = 1;
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.DiscardOldestPolicy());

    }

}
