package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Transactional
    public void insertName(String name) {
        User user = new User();
        user.setAge(28);
        user.setName(name);
        user.setSubmitTime(new Date());
        userDao.insert(user);
        System.out.println(user);
    }

    @Transactional
    public void updateById(Long id) {
        User user = new User();
        user.setId(id);
        user.setAge(23);
        userDao.updateByPrimaryKeySelective(user);
        System.out.println(user.getAge());
    }

    @Transactional
    public void updateByName(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(11);
        userDao.updateByNameSelective(user);
        System.out.println(user.getAge());

    }



}
