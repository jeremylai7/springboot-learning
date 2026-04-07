package com.test.controller;

import com.test.service.MysqlSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: 2026/4/7
 * @desc:  测试更新+异步的bug
 */
@RestController
@RequestMapping("/mysql/sync")
public class MysqlSyncController {

    @Autowired
    private MysqlSyncService mysqlSyncService;



    /**
     * 测试更新+异步的bug
     *
     */

    @GetMapping("/test-bug")
    public String test() {
        return mysqlSyncService.testBug();
    }




}
