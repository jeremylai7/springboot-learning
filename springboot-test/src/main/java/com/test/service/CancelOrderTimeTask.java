package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import com.test.util.SpringContextUtil;

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
        UserDao userDao = (UserDao) SpringContextUtil.getBean("userDao");
        User user = userDao.selectByPrimary(id);
        user.setAge(user.getAge() + 10);
        user.setSubmitTime(new Date());
        userDao.updateByNameSelective(user);


    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
