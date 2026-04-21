package com.test.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author: laizc
 * @date: 2026/4/21
 * @desc:
 */
public class AsyncTest {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

    /**
     * 多个任务异步执行
     * 耗时：最大任务时间
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("开始执行" + formatter.format(LocalDateTime.now()));
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步任务执行1完成" + formatter.format(LocalDateTime.now()));
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步任务执行2完成" + formatter.format(LocalDateTime.now()));
        });
        futures.add(future);
        futures.add(future2);
        // 等待所有异步任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("所有异步任务完成" + formatter.format(LocalDateTime.now()));

    }
}
