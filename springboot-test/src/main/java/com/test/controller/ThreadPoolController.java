package com.test.controller;

import com.test.service.ThreadPoolService;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @Author: laizc
 * @Date: Created in  2022-04-26
 * @desc: 线程池测试
 */
@RestController
@RequestMapping("/thread-pool")
public class ThreadPoolController {

	@Autowired
	private ThreadPoolService threadPoolService;

	@RequestMapping("/async")
	public String async() {
		threadPoolService.asyncTest();
		threadPoolService.noAsyncTest();
		return "test ok";
	}

	public static void main(String[] args) {
		int corePoolSize = 1;
		int maximumPoolSize = 2;
		long keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;

		// 无界队列
		BlockingQueue<Runnable> workQueue1 = new LinkedBlockingDeque<>(10);
		// 有界队列 遵循 FIFO 原则
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);
		// 有界优先队列队列  优先级由任务中的 Comparator 决定
		BlockingQueue<Runnable> workQueue2 = new PriorityBlockingQueue<>(10);
        // 线程工厂
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		// 拒绝策略 默认拒绝策略，拒绝任务并抛出任务
		RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
		// 触发拒绝策略，会使用调用线程直接运行任务
		RejectedExecutionHandler handler1 = new ThreadPoolExecutor.CallerRunsPolicy();
		// 直接拒绝任务，不抛出错误
		RejectedExecutionHandler handler2 = new ThreadPoolExecutor.DiscardPolicy();
		// 触发拒绝策略，只要还有任务新增，一直会丢弃阻塞队列的最老的任务，并将新的任务加入
		RejectedExecutionHandler handler3 = new ThreadPoolExecutor.DiscardOldestPolicy();

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize,
				maximumPoolSize,
				keepAliveTime,
				unit,
				workQueue,
				threadFactory,
				handler1);
		for (int i = 0; i < 20; i++) {
			System.out.println(i);
			try {
				threadPool.execute(new TaskThread(i));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}



	}

	static class TaskThread implements Runnable{

		private int i;

		public TaskThread(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("执行代码" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

