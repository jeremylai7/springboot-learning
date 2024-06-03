package com.jeremy;

import lombok.Builder;
import lombok.Data;

/**
 * @author: laizc
 * @date: 2024/6/3 20:21
 * @desc:
 */
@Data
public class QueryOrderDataIndexDto {

    private Integer pageNum = 1;

    /**
     * 偏移量
     */
    private Integer offset = 0;

    /**
     * 页面大小
     */
    private Integer pageSize = 10;
}
