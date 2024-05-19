package com.test.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@Data
public class DemoExcelInput {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("图片")
    private String imageUrl;

}
