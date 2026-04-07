package com.test.service;

import com.alibaba.fastjson.JSON;
import com.test.dao.UserDao;
import com.test.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: 2026/4/7
 * @desc:
 */
@Service
@Slf4j
public class MysqlSync2Service {

    @Autowired
    private UserDao userDao;


    //添加@Async注解 读取还是更新前的值
    //去掉@Async注解 读取到更新后的值
    @Async
    public void sync(Long id,Integer age) {
        User user = userDao.selectByPrimary(id);
        log.info("更新后 age: {} 查询到的 age：{}", age,user.getAge());
    }

}
