package com.jeremy.service;

import com.jeremy.dao.OrderDao;
import com.jeremy.dao.ProductDao;
import com.jeremy.model.Order;
import com.jeremy.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void addOrder(Order order) throws Exception {
        Product product = productDao.selectById(order.getProductId());
        // 第一个线程还未更新库存，后面的线程都进来获取了未更新的库存。
        // 后续线程更新库存都是更新相同的库存
        int store = product.getStore() - order.getNum();
        int num = order.getNum();
        if (store >= 0) {
            System.out.println("库存" + store + "订单数：" + num);
            // 扣库存
            product.setStore(store);
            productDao.updateByPrimaryKey(product);
            // 添加订单
            orderDao.insert(order);
        } else {
            throw new Exception("哎呦喂，库存不足");
        }
    }
}
