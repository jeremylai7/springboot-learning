package com.jeremy.handler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: laizc
 * @Date: Created in  2021-09-10
 * @desc:
 */
//@EnableScheduling
//@Component
public class DynamicCronHandler implements SchedulingConfigurer {

	private static final String DEFAULT_CORN = "0/5 * * * * ?";

	private String taskCron = DEFAULT_CORN;

	/**
	 * 这里执行的任务是固定
	 * @param scheduledTaskRegistrar
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.addTriggerTask(()->{
			runTask();
		},triggerContext -> {
			//刷新cron
			CronTrigger cronTrigger = new CronTrigger(taskCron);
			Date nextExecDate = cronTrigger.nextExecutionTime(triggerContext);
			return nextExecDate;
		});
	}

	/**
	 * 执行任务的方法
	 */
	private void runTask() {
		Date date = new Date();
		System.out.println("执行任务....." + date);
	}

	public DynamicCronHandler taskCron(String taskCron) {
		System.out.println("更新 cron:" + taskCron);
		this.taskCron = taskCron;
		return this;
	}

}
