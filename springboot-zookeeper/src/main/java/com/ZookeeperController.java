package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: laizc
 * @date: created in 2023/4/6
 * @desc:
 */
@RestController
public class ZookeeperController {

    @Autowired
    private ZkApi zkApi;

    @GetMapping("/create")
    public boolean create(String path,String data) {
        return zkApi.createNode(path,data);
    }
}
