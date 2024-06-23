package com.test.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.test.annotation.ExcelImageProperty;
import lombok.Data;

import java.io.InputStream;
import java.net.URL;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@Data
public class DemoExcelInput {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty(value = "图片",converter = ExcelUrlImageConverter.class)
    @ExcelImageProperty(index = 2)
    private String imageStr;

    @ExcelProperty("url")
    private URL imageUrl;

    @ExcelProperty("inputstream")
    private InputStream inputStream;

    @ExcelProperty("bytes")
    private byte[] bytes;






}
