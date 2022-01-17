package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.stereotype.Service;

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

    public void insertName(String name) {
        User user = new User();
        user.setAge(28);
        user.setName(name);
        user.setSubmitTime(new Date());
        userDao.insert(user);
    }

}
