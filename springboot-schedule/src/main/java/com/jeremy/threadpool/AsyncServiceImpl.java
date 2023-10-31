package com.jeremy.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2023/10/30
 * @desc: 异步执行任务
 **/
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("customerTaskExecutor")
    public void executeAsync() {
        log.info("【开始执行任务】");
        // 延迟几秒
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("【结束执行任务】");

    }
}
