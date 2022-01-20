package com.test.dao;

import com.test.model.User;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
public interface UserDao {

    User selectByPrimary(Long id);

    void insert(User user);

    void updateByPrimaryKeySelective(User user);

    void updateByNameSelective(User user);

    void updateByPrimaryKey(User user);

}
