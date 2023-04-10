package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.config.IService;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
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
public class Controller {

    @Autowired
    private ZkApi zkApi;

    @Autowired
    private IService iService;


    @GetMapping("/create")
    public boolean create(String path,String data) {
        return zkApi.createNode(path,data);
    }

    @GetMapping("/sec-kill")
    public String secKill() throws Exception {
        iService.secKill();
        return "ok";
    }

    @GetMapping("/query")
    public String query() {
        JSONObject json = iService.query();
        return "订单数：" + json.getInteger("orderNum") + ",库存剩余" + json.getInteger("storeNum");
    }
}
