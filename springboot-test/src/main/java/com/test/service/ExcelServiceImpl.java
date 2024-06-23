package com.test.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.IoUtils;
import com.test.dto.DemoExcelInput;
import com.test.util.ExcelReadImageUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService{


    @Override
    public List<DemoExcelInput> easyImport(MultipartFile multipartFile) {
        InputStream inputStream = null;
        List<DemoExcelInput> demoExcelInputs = null;
        try {
            inputStream = multipartFile.getInputStream();
            demoExcelInputs = EasyExcelFactory.read(multipartFile.getInputStream())
                .head(DemoExcelInput.class).sheet().doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReadImageUtil.readImage(inputStream,demoExcelInputs);
        log.info(demoExcelInputs.toString());
        return demoExcelInputs;
    }

    @Override
    public void easyDownload(HttpServletResponse response) throws IOException {
        List<DemoExcelInput> demoExcelInputs = new ArrayList<>();
        DemoExcelInput demoExcelInput = new DemoExcelInput();
        demoExcelInput.setName("aa");
        String url = "https://p26-passport.byteacctimg.com/img/user-avatar/82b069ce17bb5b0eccb7ee67d3f6f3bc~180x180.awebp";
        demoExcelInput.setImageStr(url);
        demoExcelInput.setImageUrl(new URL(url));
        demoExcelInputs.add(demoExcelInput);

        InputStream inputStream = new URL(url).openStream();
        demoExcelInput.setInputStream(inputStream);
        byte[] bytes = IoUtils.toByteArray(new URL(url).openStream());
        demoExcelInput.setBytes(bytes);

        // 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName= "导出excel模板";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition","attachment;filename*=utf-8''"+encodedFileName+".xlsx");
        EasyExcel.write(response.getOutputStream(),DemoExcelInput.class).sheet("模板").doWrite(demoExcelInputs);

    }

    @Override
    public void userDefinedExport(HttpServletResponse response) throws IOException, InvalidFormatException {
        List<Map<String, Object>> sheetDatas = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<DemoExcelInput> demoExcelInputList = new ArrayList<>();
        DemoExcelInput input = new DemoExcelInput();
        input.setName("aa");
        input.setImageStr("图片");
        demoExcelInputList.add(input);
        DemoExcelInput input2 = new DemoExcelInput();
        input2.setName("bb");
        input2.setImageStr("图片2");
        demoExcelInputList.add(input2);
        map.put("list", demoExcelInputList);
        map.put("fullName","腾讯企业");
        sheetDatas.add(map);
        File file = ResourceUtils.getFile("classpath:excel/statement.xlsx");

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformXLS(in, map);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        // 获取内存缓冲中的数据
        byte[] content = out.toByteArray();
        // 将字节数组转化为输入流
        InputStream inputStream = new ByteArrayInputStream(content);
        String newFile = "test.xlsx";
        // 文件中文名添加"iso-8859-1"防止乱码
        response.setHeader("Content-Disposition", "attachment; filename=" + new String((newFile).getBytes("UTF-8"), "iso-8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        byte[] buff = new byte[8192];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
        outputStream.flush();
        outputStream.close();




    }
}
