package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
@Service
public class AService {

    @Autowired
    private UserService userService;

    public void A(String name) {
        userService.insertName("a-" + name);
    }

}
