package com.test.util;

import com.test.annotation.ExcelImageProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/6/23
 * @desc:
 **/
@Slf4j
public class ExcelReadImageUtil {

    public static <T> void readImage(InputStream inputStream, List<T> list) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 默认读取第一页
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            List<POIXMLDocumentPart> documentPartList = sheet.getRelations();
            Integer size = list.size();
            for (POIXMLDocumentPart part : documentPartList) {
                if (part instanceof XSSFDrawing) {
                    XSSFDrawing drawing = (XSSFDrawing) part;
                    List<XSSFShape> shapes = drawing.getShapes();
                    for (XSSFShape shape : shapes) {
                        XSSFPicture picture = (XSSFPicture) shape;
                        XSSFClientAnchor anchor = picture.getPreferredSize();
                        CTMarker marker = anchor.getFrom();
                        int row = marker.getRow();
                        int col = marker.getCol();
                        // 从第2行开始
                        if (row > 0 && row <= size) {
                            PictureData pictureData = picture.getPictureData();
                            String extension = pictureData.suggestFileExtension();
                            byte[] bytes = pictureData.getData();
                            InputStream imageInputStream = new ByteArrayInputStream(bytes);
                            //String url = iTxCosService.uploadFile(new ByteArrayInputStream(bytes), UUID.randomUUID() + "." + extension);
                            for (int i = 0; i < size; i++) {
                                T item = list.get(i);
                                Class clazz = item.getClass();
                                Field[] fields = clazz.getDeclaredFields();
                                for (Field field : fields) {
                                    if (field.isAnnotationPresent(ExcelImageProperty.class)) {
                                        ExcelImageProperty excelImageProperty = field.getAnnotation(ExcelImageProperty.class);
                                        int index = excelImageProperty.index();
                                        if (index == col + 1 && row - 1 == i) {
                                            field.setAccessible(true);
                                            field.set(item,new String(bytes));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            log.error("read image error {}",e);

        }
    }
}
