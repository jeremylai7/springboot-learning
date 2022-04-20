package com.test.controller;

import com.test.dao.UserDao;
import com.test.model.User;
import com.test.service.CancelOrderTimeTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Resource
    private UserDao userDao;

    private int index = 0;

    @GetMapping("/test")
    public String test() {
        index++;
        User user = new User();
        user.setAge(index);
        user.setName(index + "hah");
        user.setSubmitTime(new Date());
        userDao.insert(user);
        Timer timer = new Timer();
        CancelOrderTimeTask timeTask = new CancelOrderTimeTask(user.getId());
        timer.schedule(timeTask,10 * 1000);
        return "ok";
    }

    AtomicInteger integer = new AtomicInteger(0);

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
