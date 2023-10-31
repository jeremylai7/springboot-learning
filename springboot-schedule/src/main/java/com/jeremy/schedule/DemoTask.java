package com.jeremy.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: laizc
 * @Date: Created in  2021-09-10
 * @desc:
 */
@Component
public class DemoTask {

	//@Scheduled(fixedDelay = 1000*10)
	public void test() {
		System.out.println("task1 start");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date date = new Date();
		System.out.println("task1 " + date);
	}

	//@Scheduled(fixedDelay = 1000*2)
	public void test2() {
		Date date = new Date();
		System.out.println("tesk2 " + date);
	}
}
