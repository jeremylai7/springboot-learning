package com.test;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.junit.Test;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/4/4
 * @desc:
 **/
public class FastJsonTest {

    @Test
    public void test() {
        // 测试 JSONArray.parseArray 大小写 1.1.22  1.1.23 忽略了大小写
        String jsonStr = "[{\"AA\":\"bb\"}]";
        List<Fast> list = JSONArray.parseArray(jsonStr,Fast.class);
        System.out.println(list.get(0).getAa());
    }

    @Data
    static class Fast {
        private String aa;
    }


}
