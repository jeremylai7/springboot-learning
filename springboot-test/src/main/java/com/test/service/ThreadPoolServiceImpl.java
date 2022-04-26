package com.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-26
 * @desc:
 */
@Service
@Slf4j
public class ThreadPoolServiceImpl implements ThreadPoolService{

	@Override
	@Async
	public void asyncTest() {
		log.info("asyncTest");
	}

	@Override
	public void noAsyncTest() {
		log.info("no-asyncTest");
	}
}
