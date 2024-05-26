package com.test.controller;

import java.util.*;

/**
 * @author: laizc
 * @date: created in 2024/5/25
 * @desc:
 **/
public class SaleStaticController {

    public static void main(String[] args) {
        List<List<String>> dailySales = Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("a", "c", "a"),
                Arrays.asList("b", "c", "b")
                // 可以继续添加更多天的销量数据
        );
        // 所有商品榜首天数
        Map<String, Integer> topDays = new HashMap<>();
        // 所有商品前三天数
        Map<String, Integer> topThreeDays = new HashMap<>();

        // 初始化所有商品的计数器
        for (List<String> day : dailySales) {
            for (String product : day) {
                topDays.put(product, 0);
                topThreeDays.put(product, 0);
            }
        }

        Map<String, Integer> currentTopStreak = new HashMap<>();
        Map<String, Integer> currentTopThreeStreak = new HashMap<>();
        for (String product : topDays.keySet()) {
            currentTopStreak.put(product, 0);
            currentTopThreeStreak.put(product, 0);
        }
        // 记录上一个榜首
        String lastTopProduct = null;
        // 记录上一个前三商品
        Set<String> lastTopThreeProducts = new HashSet<>();

        // 统计每天的排行情况
        for (int i = 0; i < dailySales.size(); i++) {
            List<String> day = dailySales.get(i);
            String topProduct = day.get(0);
            // 获取前三数据
            Set<String> currentTopThree = new HashSet<>(day.subList(0, Math.min(3, day.size())));

            // 更新榜首计数
            if (topProduct.equals(lastTopProduct)) {
                currentTopStreak.put(topProduct, currentTopStreak.get(topProduct) + 1);
            } else {
                currentTopStreak.put(topProduct, 1);
                lastTopProduct = topProduct;
            }
            topDays.put(topProduct, Math.max(topDays.get(topProduct), currentTopStreak.get(topProduct)));

            // 更新前三计数
            for (String product : topThreeDays.keySet()) {
                if (currentTopThree.contains(product)) {
                    if (lastTopThreeProducts.contains(product)) {
                        currentTopThreeStreak.put(product, currentTopThreeStreak.get(product) + 1);
                    } else {
                        currentTopThreeStreak.put(product, 1);
                    }
                } else {
                    currentTopThreeStreak.put(product, 0);
                }
                topThreeDays.put(product, Math.max(topThreeDays.get(product), currentTopThreeStreak.get(product)));
            }

            lastTopThreeProducts = currentTopThree;
        }

        // 输出每个商品占据榜首的连续天数和连续进入前三的天数
        System.out.println("商品连续占据榜首的最长天数:");
        for (Map.Entry<String, Integer> entry : topDays.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " 天");
        }

        System.out.println("商品连续进入前三的最长天数:");
        for (Map.Entry<String, Integer> entry : topThreeDays.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " 天");
        }
    }
}
