package com.test.controller;

import com.test.dto.DemoExcelInput;
import com.test.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/19
 * @desc:
 **/
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * easy excel 导入 excel
     * @param multipartFile
     */
    @PostMapping("/easy-import")
    public List<DemoExcelInput> easyImport(MultipartFile multipartFile) {
        List<DemoExcelInput> list = excelService.easyImport(multipartFile);
        return list;
    }

    @GetMapping("/easy-download")
    public void easyDownload(HttpServletResponse response) {
        excelService.easyDownload(response);
    }


}
