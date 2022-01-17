package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
@Service
public class BService {

    @Autowired
    private UserService userService;

    public void B(String name) {
        userService.insertName("b-" + name);
    }

    public void B2(String name) {
        userService.insertName("b2-" + name);
    }
}
