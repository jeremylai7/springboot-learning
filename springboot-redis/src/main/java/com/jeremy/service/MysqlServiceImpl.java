package com.jeremy.service;

import com.jeremy.dao.OrderDao;
import com.jeremy.dao.ProductDao;
import com.jeremy.exception.BusinessException;
import com.jeremy.model.Order;
import com.jeremy.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author: laizc
 * @date: created in 2023/3/28
 * @desc:
 */
@Service
@Slf4j
public class MysqlServiceImpl implements MysqlService{

    @Resource
    private OrderDao orderDao;

    @Resource
    private ProductDao productDao;

    @Override
    @Transactional
    public void addOrder(Order order) throws Exception {
        // mysql 行锁 搭配 @Transactional 实现同步锁
        //Product product = productDao.selectByIdForUpdate(order.getProductId());
        Product product = productDao.selectByPrimaryKey(order.getProductId());
        // 第一个线程还未更新库存，后面的线程都进来获取了未更新的库存。
        // 后续线程更新库存都是更新相同的库存
        int store = product.getStore() - order.getNum();
        if (store >= 0) {
            log.info("库存:" + store );
            // 扣库存
            product.setStore(store);
            productDao.updateByPrimaryKey(product);
            // 添加订单
            orderDao.insert(order);
        } else {
            throw new Exception("哎呦喂，库存不足");
        }
    }

    @Override
    public void seckill(Long productId) throws BusinessException {
        //1.查询该商品库存，为0则活动结束。
        Product product = productDao.selectByIdForUpdate(productId);
        long stockNum = product.getStore();
        if(stockNum == 0) {
            throw new BusinessException("活动结束");
        }else {
            //2.下单(模拟不同用户openid不同)
            Order order = new Order(null, UUID.randomUUID().toString(),1,3,productId,new Date());
            orderDao.insert(order);
            //3.减库存
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            productDao.updateStoreById(stockNum,productId);
        }
    }
}
