package com.test.controller;

import com.test.service.CancelOrderTimeTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * @author: laizc
 * @date: created in 2022/1/23
 * @desc:
 **/
@RestController
public class TimerController {


    @GetMapping("/timer")
    public String timer(long id) {
        Timer timer = new Timer();
        CancelOrderTimeTask timeTask = new CancelOrderTimeTask(id);
        System.out.println("当前时间是" + getCurrentTime());
        //三秒执行任务
        timer.schedule(timeTask,10 * 1000);
        return "ok";
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
