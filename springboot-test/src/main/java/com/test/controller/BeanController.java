package com.test.controller;

import com.test.model.Hello;
import com.test.util.SpringContextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2023/7/28
 * @desc:
 */
@RestController
@RequestMapping("/bean")
public class BeanController {

    @GetMapping("/getBean")
    public void getBean() {
        Hello hello = (Hello) SpringContextUtil.getBean("hello");
        System.out.println(hello);
        Hello hello2 = (Hello) SpringContextUtil.getBean("hello");
        System.out.println(hello2);


    }

}
