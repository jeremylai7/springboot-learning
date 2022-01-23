package com.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author: laizc
 * @date: created in 2022/1/23
 * @desc:
 **/
public class CancelOrderTimeTask extends TimerTask {

    private Long id;

    public CancelOrderTimeTask(long id) {
        this.id = id;
    }


    @Override
    public void run() {
        // 执行取消订单
        System.out.println(getCurrentTime() + " 时间取消订单,订单id：" + id);

    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
