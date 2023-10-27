package com.jeremy.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @Author: laizc
 * @Date: Created in  2021-09-10
 * @desc:
 */
//@Component
@EnableScheduling
public class DynamicTaskScheduler {

	private ScheduledFuture<?> future;

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	/**
	 * 这里执行的任务是传参进来的
	 * @param task
	 * @param cron
	 */
	public void startCron(Runnable task,String cron) {
		stopCron();
		future = threadPoolTaskScheduler.schedule(task,new CronTrigger(cron));
	}

	private void stopCron() {
		if (future != null) {
			future.cancel(true);
			System.out.println("stopCron.......");
		}
	}

}
