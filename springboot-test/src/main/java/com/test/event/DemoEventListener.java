package com.test.event;

import com.test.dto.DemoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: laizc
 * @date: created in 2024/12/4
 * @desc:
 **/
@Component
@Slf4j
public class DemoEventListener {

    @EventListener
    @Async
    public void handleEvent(DemoEvent event){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(event.getName());
        log.info("事件监听");

    }
}
