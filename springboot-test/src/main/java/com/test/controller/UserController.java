package com.test.controller;

import com.test.model.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author: laizc
 * @date: 2025/11/6 14:29
 * @desc:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/addUser")
    public String addUser() {
        User user = new User();
        user.setName(UUID.randomUUID().toString().replace("-", ""));
        user.setSubmitTime(new Date());
        Random random = new Random();
        // 10-20
        int randomInt = random.nextInt(11) + 10;
        user.setAge(randomInt);
        userService.addUser(user);
        return "ok";
    }
}
