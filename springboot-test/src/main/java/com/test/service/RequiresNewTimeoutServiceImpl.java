package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * 需求点：1、更新订单审核状态，2,3步骤报错，不能影响 1步骤。
     * 2,3步骤又是一起的。一起失败，一起成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderPass() {
        // 1、更新订单审核状态
        updateOrderAuditStatus(id);
        // 2、添加入库
        addPutInStorage(id);
        // 3、更新订单入库状态
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
        User user = userDao.selectByPrimary(id);
        user.setName(user.getName() + "@");
        userDao.updateByPrimaryKeySelective(user);
    }


}
