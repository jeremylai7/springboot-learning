package com.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.common.ZKConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: laizc
 * @date: created in 2023/4/6
 * @desc:
 */
@Configuration
@Slf4j
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Bean
    public ZooKeeper zooKeeper() {
        ZooKeeper zooKeeper = null;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        // 收到服务端响应，连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【初始化连接状态】.....{}",zooKeeper.getState());
        } catch (Exception e) {
            log.error("【连接异常】 .....{}",e);
        }
        return zooKeeper;
    }

    /**
     * curator 工具类封装实现了分布式锁
     * @return
     */
    @Bean
    public InterProcessMutex interProcessMutex() {
        CuratorFramework zkClient = getZkClient();
        String lockPath = "/lock";
        InterProcessMutex lock = new InterProcessMutex(zkClient,lockPath);
        return lock;
    }

    private CuratorFramework getZkClient() {
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000,3,5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retry).build();
        zkClient.start();
        return zkClient;
    }




}
