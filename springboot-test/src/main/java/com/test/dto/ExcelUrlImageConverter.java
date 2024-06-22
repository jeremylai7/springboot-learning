package com.test.dto;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.util.StringUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * @author: laizc
 * @date: created in 2024/6/22
 * @desc: 导出 图片转换器
 **/
public class ExcelUrlImageConverter implements Converter<String> {

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) throws Exception {
        String urlString = context.getValue();
        if (StringUtils.isBlank(urlString)) {
            return new WriteCellData<>("");
        }
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        byte[] bytes = IoUtils.toByteArray(inputStream);
        return new WriteCellData<>(bytes);
    }
}
