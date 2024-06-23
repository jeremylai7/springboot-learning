package com.test.service;

import com.test.dto.DemoExcelInput;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
public interface ExcelService {

    List<DemoExcelInput> easyImport(MultipartFile multipartFile);

    void easyDownload(HttpServletResponse response) throws IOException;

    void userDefinedExport(HttpServletResponse response) throws IOException, InvalidFormatException;
}
