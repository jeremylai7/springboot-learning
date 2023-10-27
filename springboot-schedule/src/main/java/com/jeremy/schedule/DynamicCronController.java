package com.jeremy.schedule;

import com.jeremy.handler.DynamicCronHandler;
import com.jeremy.handler.DynamicTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Author: laizc
 * @Date: Created in  2021-09-10
 * @desc:
 */
@RestController
@RequestMapping("/dynamic-cron")
public class DynamicCronController {

	//@Autowired
	//private DynamicCronHandler dynamicCronHandler;

	@GetMapping("/test1")
	public Object test(String cron) {
		if (cron == null) {
			cron = "0/2 * * * * ?";
		}
		//dynamicCronHandler.taskCron(cron);
		return null;
	}

	//@Autowired
	private DynamicTaskScheduler dynamicTaskScheduler;



	@GetMapping("/test2")
	public Object task(String cron) {
		if (cron == null) {
			cron = "0/2 * * * * ?";
		}
		int index;
		dynamicTaskScheduler.startCron(()->{
			//任务执行的地方
			Date date = new Date();
			System.out.println("执行task 任务" + date);
		},cron);
		return null;
	}

}
