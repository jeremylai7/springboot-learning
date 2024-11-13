package com.test.service;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tiia.v20190529.models.ImageInfo;
import com.test.dto.AddImageDTO;
import com.test.dto.DeleteImageDTO;
import com.test.dto.SearchRequest;

import java.io.IOException;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/11/12
 * @desc:
 **/
public interface ImageAnalysisService {

    ImageInfo [] analysis(SearchRequest request) throws IOException, TencentCloudSDKException;


    void deleteImage(List<DeleteImageDTO> list);

    void uploadImage(List<AddImageDTO> list);
}
