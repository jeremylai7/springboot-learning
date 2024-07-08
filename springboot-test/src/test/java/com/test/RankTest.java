package com.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: laizc
 * @date: created in 2024/7/7
 * @desc:
 **/
public class RankTest {

    @Test
    public void test() {
        RankDetailLog detailLog7 = new RankDetailLog("C",1,"7月4日");
        RankDetailLog detailLog8 = new RankDetailLog("A",2,"7月4日");
        RankDetailLog detailLog9 = new RankDetailLog("B",3,"7月4日");
        RankDetailLog detailLog1 = new RankDetailLog("D",1,"7月2日");
        RankDetailLog detailLog2 = new RankDetailLog("B",2,"7月2日");
        RankDetailLog detailLog3 = new RankDetailLog("C",3,"7月2日");
        RankDetailLog detailLog4 = new RankDetailLog("B",1,"7月3日");
        RankDetailLog detailLog5 = new RankDetailLog("C",2,"7月3日");
        RankDetailLog detailLog6 = new RankDetailLog("A",3,"7月3日");

        List<RankDetailLog> detailLogList = new ArrayList<>();
        detailLogList.add(detailLog1);
        detailLogList.add(detailLog2);
        detailLogList.add(detailLog3);
        detailLogList.add(detailLog4);
        detailLogList.add(detailLog5);
        detailLogList.add(detailLog6);
        detailLogList.add(detailLog7);
        detailLogList.add(detailLog8);
        detailLogList.add(detailLog9);
        // 按日期分组并排序
        Map<String, List<RankDetailLog>> sortedCreateTimeMap = detailLogList.stream()
                .collect(Collectors.groupingBy(RankDetailLog::getCreateTimeStr, () -> new TreeMap<>(Comparator.reverseOrder()), Collectors.toList()));
        String topProduct = null;
        Integer topProductNum = 0;
        for (Map.Entry<String, List<RankDetailLog>> entry : sortedCreateTimeMap.entrySet()) {
            List<RankDetailLog> rankDetailLogList = entry.getValue();
            if (!rankDetailLogList.isEmpty()) {
                RankDetailLog topLog = rankDetailLogList.get(0);
                String currentTopProduct = topLog.getProduct();
                if (topProduct == null) {
                    topProduct = currentTopProduct;
                    topProductNum = 1;
                } else {
                    if (topProduct.equals(currentTopProduct)) {
                        topProductNum++;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("榜首商品：" + topProduct + "，天数：" + topProductNum);

        Set<String> threeProductSet = new HashSet<>();
        Map<String,Integer> threeProductMap = new HashMap<>();
        boolean first = true;
        for (Map.Entry<String, List<RankDetailLog>> entry : sortedCreateTimeMap.entrySet()) {
            List<RankDetailLog> rankDetailLogList = entry.getValue();
            if (!rankDetailLogList.isEmpty()) {
                // 只取前三数据
                rankDetailLogList = rankDetailLogList.subList(0,Math.min(3,rankDetailLogList.size()));
                if (first) {
                    for (int i = 0; i < rankDetailLogList.size(); i++) {
                        RankDetailLog detailLog = rankDetailLogList.get(i);
                        String topThreeProduct = detailLog.getProduct();
                        if (i >= 1) {
                            threeProductSet.add(topThreeProduct);
                            threeProductMap.put(topThreeProduct,1);
                        }
                    }
                    first = false;
                } else {
                    Set<String> currentThreeProductSet = new HashSet<>();
                    for (RankDetailLog detailLog : rankDetailLogList) {
                        String topThreeProduct = detailLog.getProduct();
                        if (threeProductMap.containsKey(topThreeProduct)) {
                            threeProductMap.put(topThreeProduct,threeProductMap.get(topThreeProduct) + 1);
                            currentThreeProductSet.add(topThreeProduct);
                        }
                    }
                    threeProductSet.removeAll(currentThreeProductSet);
                }
            }
        }
        System.out.println(threeProductMap);

    }

    @Data
    @AllArgsConstructor
    class RankDetailLog{

        /**
         * 商品
         */
        private String product;

        /**
         * 排行
         */
        private Integer rank;

        /**
         * 时间
         */
        private String createTimeStr;

    }
}
