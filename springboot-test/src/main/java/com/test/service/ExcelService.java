package com.test.service;

import com.test.dto.DemoExcelInput;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
public interface ExcelService {

    List<DemoExcelInput> easyImport(MultipartFile multipartFile);

    void easyDownload(HttpServletResponse response);
}
