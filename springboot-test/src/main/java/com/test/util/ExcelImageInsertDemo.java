package com.test.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelImageInsertDemo {

    /**
     * 图片链接所在列，下标从 0 开始
     * A列=0，B列=1，C列=2
     */
    private static final int IMAGE_URL_COL_INDEX = 1;

    /**
     * 新增图片列，下标从 0 开始
     * D列=3
     */
    private static final int IMAGE_PREVIEW_COL_INDEX = 3;


    /**
     * 读取图片链接，读取图片填充到 excel
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String inputPath = "D:/aaa.xlsx";
        String outputPath = "D:/output.xlsx";

        insertImageColumn(inputPath, outputPath);
    }

    public static void insertImageColumn(String inputPath, String outputPath) throws Exception {
        // 1. 用 EasyExcel 读取图片链接
        Map<Integer, String> rowImageUrlMap = readImageUrls(inputPath);

        // 2. 用 POI 打开原 Excel
        try (InputStream inputStream = new FileInputStream(inputPath);
             Workbook workbook = new XSSFWorkbook(inputStream);
             FileOutputStream outputStream = new FileOutputStream(outputPath)) {

            Sheet sheet = workbook.getSheetAt(0);

            // 3. 设置新增列表头
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }
            headerRow.createCell(IMAGE_PREVIEW_COL_INDEX).setCellValue("图片预览");

            // 4. 设置列宽
            sheet.setColumnWidth(IMAGE_PREVIEW_COL_INDEX, 20 * 256);

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            CreationHelper helper = workbook.getCreationHelper();

            // 5. 遍历每一行图片链接，下载并插入图片
            for (Map.Entry<Integer, String> entry : rowImageUrlMap.entrySet()) {
                Integer rowIndex = entry.getKey();
                String imageUrl = entry.getValue();

                if (StrUtil.isBlank(imageUrl)) {
                    continue;
                }

                // 如果一个单元格里有多个图片链接，只取第一张
                imageUrl = imageUrl.split(",")[0];

                try {
                    byte[] imageBytes = downloadImage(imageUrl);
                    if (imageBytes == null || imageBytes.length == 0) {
                        continue;
                    }

                    int pictureType = getPictureType(imageUrl);
                    int pictureIndex = workbook.addPicture(imageBytes, pictureType);

                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        row = sheet.createRow(rowIndex);
                    }

                    // 设置行高
                    row.setHeightInPoints(85);

                    ClientAnchor anchor = helper.createClientAnchor();
                    anchor.setCol1(IMAGE_PREVIEW_COL_INDEX);
                    anchor.setRow1(rowIndex);
                    anchor.setCol2(IMAGE_PREVIEW_COL_INDEX + 1);
                    anchor.setRow2(rowIndex + 1);

                    drawing.createPicture(anchor, pictureIndex);

                } catch (Exception e) {
                    System.out.println("第 " + (rowIndex + 1) + " 行图片插入失败：" + imageUrl);
                    e.printStackTrace();
                }
            }

            workbook.write(outputStream);
        }
    }

    /**
     * 用 EasyExcel 读取图片链接列
     */
    private static Map<Integer, String> readImageUrls(String inputPath) {
        Map<Integer, String> rowImageUrlMap = new LinkedHashMap<>();

        EasyExcel.read(inputPath, new AnalysisEventListener<Map<Integer, String>>() {

            @Override
            public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
                Integer rowIndex = context.readRowHolder().getRowIndex();

                // 跳过表头
                if (rowIndex == 0) {
                    return;
                }

                String imageUrl = rowData.get(IMAGE_URL_COL_INDEX);
                if (StrUtil.isNotBlank(imageUrl)) {
                    rowImageUrlMap.put(rowIndex, imageUrl);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).sheet(0).doRead();

        return rowImageUrlMap;
    }

    /**
     * 下载图片
     */
    private static byte[] downloadImage(String imageUrl) {
        return HttpRequest.get(imageUrl)
                .timeout(10000)
                .execute()
                .bodyBytes();
    }

    /**
     * 判断图片类型
     */
    private static int getPictureType(String imageUrl) {
        String lowerUrl = imageUrl.toLowerCase();

        if (lowerUrl.endsWith(".png")) {
            return Workbook.PICTURE_TYPE_PNG;
        }

        if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg")) {
            return Workbook.PICTURE_TYPE_JPEG;
        }

        // 默认按 jpg 处理
        return Workbook.PICTURE_TYPE_JPEG;
    }
}