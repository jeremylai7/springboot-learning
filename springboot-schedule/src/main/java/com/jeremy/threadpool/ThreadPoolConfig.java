package com.jeremy.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author: laizc
 * @date: created in 2023/10/30
 * @desc: 自定义线程池
 **/
@Configuration
public class ThreadPoolConfig {

    // 线程存活时间
    private static int keepAliveTime = 10;

    // 调用线程运行多余任务
    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

    @Bean("customerTaskExecutor")
    public TaskExecutor taskExecutor() {
        // 核心线程数
        int cores = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores);
        executor.setMaxPoolSize(cores);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setRejectedExecutionHandler(handler);
        executor.setThreadNamePrefix("Custom-");  // 线程名前缀
        executor.initialize();
        return executor;
    }
}
