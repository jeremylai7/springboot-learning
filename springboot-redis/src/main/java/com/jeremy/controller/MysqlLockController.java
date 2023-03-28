package com.jeremy.controller;

import com.jeremy.dao.OrderDao;
import com.jeremy.model.Order;
import com.jeremy.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author: laizc
 * @date: created in 2023/3/28
 * @desc: mysql 实现分布式锁
 */
@RestController
@RequestMapping("/mysql")
public class MysqlLockController {

    @Resource
    private MysqlService mysqlService;

    @GetMapping("/order")
    public String order() {
        Order order = new Order();
        order.setNum(2);
        order.setPrice(2);
        order.setCreateTime(new Date());
        order.setSn(UUID.randomUUID().toString());
        order.setProductId(1L);
        mysqlService.addOrder(order);
        return "ok";
    }
}
