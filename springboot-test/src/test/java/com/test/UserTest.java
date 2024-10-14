package com.test;

import com.test.dao.UserDao;
import com.test.model.User;
import com.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: created in 2022-08-24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Test
    public void testAdd() {
        userService.insertName("001");
    }

    @Test
    public void testUpdate() {
        userService.updateById(6L);
    }

    @Test
    public void testUpdateName() {
        userService.updateByName("哈哈");
    }

    @Test
    public void testSelect() {
        userService.selectById(6L);
    }

    @Test
    public void selectList() {
        Map<String,Object> map = new HashMap<>();
        String name = "0";
        map.put("name",name+"AAAA");
        List<User> list = userDao.selectList(map);
        System.out.println(list);

    }



}
