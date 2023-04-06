package com;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: laizc
 * @date: created in 2023/4/6
 * @desc:
 */
@Configuration
@Slf4j
public class ZkApi {

    @Autowired
    private ZooKeeper zooKeeper;

    /**
     *  判断指定节点是否存在
     * @param path
     * @param needWatch
     * @return
     */
    public Stat exists(String path,boolean needWatch) {
        try {
            return zooKeeper.exists(path,needWatch);
        } catch (Exception e) {
            log.error("【判断指定连接是否存在异常】{}",e);
            return null;
        }
    }

    /**
     *  检测节点是否存在，并设置监听事件
     *  三种监听类型：创建，删除，更新
     * @param path
     * @param watcher
     * @return
     */
    public Stat exists(String path, Watcher watcher) {
        try {
            return zooKeeper.exists(path,watcher);
        } catch (Exception e) {
            log.error("【判断指定连接是否存在异常】{}",e);
            return null;
        }
    }

    /**
     * 创建持久节点
     * @param path
     * @param data
     * @return
     */
    public boolean createNode(String path,String data) {
        try {
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            log.error("【创建持久节点异常】{}",e);
            return false;
        }
    }

    /**
     * 修改持久节点
     * @param path
     * @param data
     * @return
     */
    public boolean updateNode(String path,String data) {
        try {
            zooKeeper.setData(path,data.getBytes(),-1);
            return true;
        } catch (Exception e) {
            log.error("【修改节点异常】{}",e);
            return false;
        }
    }



}
