package com.jeremy.controller;

import com.jeremy.dao.OrderDao;
import com.jeremy.dao.ProductDao;
import com.jeremy.model.Order;
import com.jeremy.model.Product;
import com.jeremy.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private ProductDao productDao;

    @Resource
    private OrderDao orderDao;

    /**
     *
     * 并发下单，使用ab压测
     * 1、先执行，alter.sql 文件，在本项目的 resources/sql 目录下
     * 2、执行命令：ab -n 100 -c 20 http://localhost:8080/mysql/order?productId=1 模拟并发问题
     * 3、查看库存和订单数量：http://localhost:8080/mysql/find-order
     *
     */
    @GetMapping("/order")
    public String order(@RequestParam Long productId) throws Exception {
        Order order = new Order();
        order.setNum(1);
        order.setPrice(10);
        order.setCreateTime(new Date());
        order.setSn(UUID.randomUUID().toString());
        order.setProductId(productId);
        mysqlService.addOrder(order);
        return "下单成功";
    }

    @GetMapping("/find-order")
    public String findOrder() {
        Product product = productDao.selectByPrimaryKey(1L);
        int orderCount = orderDao.selectCount(null);
        return "库存剩余："+ product.getStore() + "，订单数量：" + orderCount;


    }
}
