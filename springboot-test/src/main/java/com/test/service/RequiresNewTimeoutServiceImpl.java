package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: 2026/10/12
 * @desc:
 */
@Service
public class RequiresNewTimeoutServiceImpl implements RequiresNewTimeoutService {

    private static Long id = 201967L;

    @Autowired
    private UserDao userDao;


    @Override
    public void orderPass() {
        // 更新订单审核状态
        updateOrderAuditStatus(id);
        // 添加入库
        addPutInStorage(id);
        // 更新订单入库状态
        updateOrderStorageStatus(id);

    }


    private void updateOrderAuditStatus(Long id) {
        User user = userDao.selectByPrimary(id);
        user.setAge(user.getAge() + 1);
        userDao.updateByPrimaryKeySelective(user);
    }


    private void addPutInStorage(Long id) {
        // 远程调用，可能会报错模拟报错
        if (id%2 == 0) {
            throw new RuntimeException("远程调用错误");
        }



    }

    private void updateOrderStorageStatus(Long id) {

    }


}
