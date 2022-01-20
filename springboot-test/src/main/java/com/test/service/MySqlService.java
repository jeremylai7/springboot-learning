package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: laizc
 * @Date: Created in  2022-01-20
 * @desc:
 */
@Service
public class MySqlService {

	@Resource
	private UserDao userDao;

	@Transactional
	public void test1(long id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setSubmitTime(new Date());
		userDao.updateByPrimaryKeySelective(user);
		try {
			//休眠十秒
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void test2(long id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setSubmitTime(new Date());
		userDao.updateByPrimaryKeySelective(user);
	}

	@Transactional
	public void test3(String name) {
		User user = new User();
		user.setName(name);
		user.setSubmitTime(new Date());
		userDao.updateByNameSelective(user);
		try {
			//休眠十秒
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void test4(String name) {
		User user = new User();
		user.setName(name);
		user.setSubmitTime(new Date());
		userDao.updateByNameSelective(user);
	}

}
