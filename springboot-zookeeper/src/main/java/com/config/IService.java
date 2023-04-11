package com.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: laizc
 * @date: created in 2023/4/9
 * @desc:
 **/
@Service
public class IService {

    private static Map<String,Integer> order = new HashMap<>();

    private static Map<String,Integer> store = new HashMap<>();

    static {
        store.put("a",100);
        order.put("a",0);

    }


    @Autowired
    private InterProcessMutex interProcessMutex;

    private int index = 0;

    public void secKill() throws Exception {
        index++;
        // 获取锁
        //interProcessMutex.acquire();
        // 根据业务获取超时时间
        interProcessMutex.acquire(3, TimeUnit.SECONDS);

        // 扣减库存,创建订单
        int num = store.get("a");
        if (num > 0) {
            store.put("a",num - 1);
            int orderNum = order.get("a");
            Thread.sleep(100);
            order.put("a",orderNum + 1);
            if (index == 3) {
                throw new Exception("系统错误");
            }
        } else {
            interProcessMutex.release();
            throw new Exception("库存不够");
        }
        interProcessMutex.release();

    }

    public JSONObject query() {
        JSONObject json = new JSONObject();
        int orderNum = order.get("a");
        int storeNum = store.get("a");
        json.put("orderNum",orderNum);
        json.put("storeNum",storeNum);
        return json;
    }
}
