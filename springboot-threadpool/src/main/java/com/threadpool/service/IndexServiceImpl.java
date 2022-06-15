package com.threadpool.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: created in 2022-06-15
 */
@Service
public class IndexServiceImpl implements IndexService{

    @Override
    @Async("asyncThreadPool")
    public void sendMessage(String message) {
        System.out.println("当前线程池名称：" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送消息");
    }


}
