package com.jeremy.service;

import com.jeremy.dao.OrderDao;
import com.jeremy.dao.ProductDao;
import com.jeremy.model.Order;
import com.jeremy.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: laizc
 * @date: created in 2023/3/28
 * @desc:
 */
@Service
public class MysqlServiceImpl implements MysqlService{

    @Resource
    private OrderDao orderDao;

    @Resource
    private ProductDao productDao;

    @Override
    public void addOrder(Order order) {
        Product product = productDao.selectByPrimaryKey(order.getProductId());
        int store = product.getStore();
        int num = order.getNum();
        if (store >= num) {
            // 扣库存
            product.setStore(store - num);
            System.out.println("剩余库存" + (store - num));
            productDao.updateByPrimaryKey(product);
            // 添加订单
            orderDao.insert(order);
        }
    }
}
