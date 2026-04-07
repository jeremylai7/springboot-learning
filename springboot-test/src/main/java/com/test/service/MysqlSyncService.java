package com.test.service;

import com.alibaba.fastjson.JSON;
import com.test.dao.UserDao;
import com.test.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: laizc
 * @date: 2026/4/7
 * @desc:
 */
@Service
@Slf4j
public class MysqlSyncService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MysqlSync2Service mysqlSync2Service;


    @Transactional(rollbackFor = Exception.class)
    public String testBug() {
        Long id = 76858L;
        User user = userDao.selectByPrimary(id);
        Integer age = user.getAge() + 1;
        user.setAge(age);
        log.info("更新后：{}", JSON.toJSONString(user));
        userDao.updateByNameSelective(user);
        mysqlSync2Service.sync(id,age);
        return "ok";
    }
}
