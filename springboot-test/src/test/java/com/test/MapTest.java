package com.test;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author: laizc
 * @date: created in 2024/6/26
 * @desc:
 **/
public class MapTest {

    @Test
    public void treeMapTest() {
        Map<Integer,String> map = new TreeMap<>();
        map.put(4,"a");
        map.put(5,"b");
        map.put(2,"c");
        System.out.println(map);



    }
}
