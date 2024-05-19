package com.test.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.test.dto.DemoExcelInput;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@Service
public class ExcelServiceImpl implements ExcelService{


    @Override
    public List<DemoExcelInput> easyImport(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<DemoExcelInput> meetingStockUpList = EasyExcelFactory.read(inputStream)
                .head(DemoExcelInput.class).sheet().doReadSync();
        return meetingStockUpList;
    }

    @Override
    public void easyDownload(HttpServletResponse response) {
        List<DemoExcelInput> demoExcelInputs = new ArrayList<>();
        DemoExcelInput demoExcelInput = new DemoExcelInput();
        demoExcelInput.setName("aa");
        demoExcelInput.setImageUrl("图片1");
        demoExcelInputs.add(demoExcelInput);
        EasyExcel.write();

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName= null;
        try {
            fileName = URLEncoder.encode("测试","UTF-8").replaceAll("\\+","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition","attachment;filename*=utf-8''"+fileName+".xlsx");
        try {
            EasyExcel.write(response.getOutputStream(),DemoExcelInput.class).sheet("模板").doWrite(demoExcelInputs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
