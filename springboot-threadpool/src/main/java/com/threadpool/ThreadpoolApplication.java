package com.threadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步支持
public class ThreadpoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadpoolApplication.class, args);
    }

}
