package com.test.controller;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.tiia.v20190529.models.ImageInfo;
import com.test.dto.AddImageDTO;
import com.test.dto.DeleteImageDTO;
import com.test.dto.SearchRequest;
import com.test.service.ImageAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author: laizc
 * @date: created in 2024/11/14
 * @desc:
 **/
@RestController
@RequestMapping("/image/search")
public class ImageSearchController {

    /**
     * 显示这个图片
     */
    private static final String[] IMG_SUFFIX = {"png", "jpg", "jpeg", "bmp"};

    @Autowired
    private ImageAnalysisService imageAnalysisService;

    //检索图片
    @PostMapping("/analysis")
    public ImageInfo [] analysis(@RequestPart("file") MultipartFile file, @RequestParam("request") String requestJson) throws Exception {
        SearchRequest request;
        if (StringUtils.isNotBlank(requestJson)) {
            request = JSON.parseObject(requestJson, SearchRequest.class);
        } else {
            request = new SearchRequest();
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        boolean isInArray = Arrays.asList(IMG_SUFFIX).contains(suffix);
        if (!isInArray) {
            throw new Exception("文件类型不支持");
        }
        request.setBytes(inputStreamToByteArray(file.getInputStream()));
        return imageAnalysisService.analysis(request);
    }

    // 创建图片
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestBody AddImageDTO addImageDTO) {
        imageAnalysisService.uploadImage(Collections.singletonList(addImageDTO));
    }

    // 删除图片
    @PostMapping("/deleteImage")
    public void deleteImage(@RequestBody DeleteImageDTO deleteImageDTO) {
        imageAnalysisService.deleteImage(Collections.singletonList(deleteImageDTO));
    }



    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

}
