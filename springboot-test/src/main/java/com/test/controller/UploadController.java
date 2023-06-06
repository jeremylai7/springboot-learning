package com.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: laizc
 * @date: created in 2023/4/20
 * @desc:
 */
@RestController
public class UploadController {

    @PostMapping("/upload")
    public String test(MultipartFile multipartFile) throws IOException {
        File excelFile = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");
        multipartFile.transferTo(excelFile);
        return "ok";

    }

}
