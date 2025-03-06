package com.test.service;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.models.SyncImage;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.*;
import com.test.config.TencentCloudTiiaProperties;
import com.test.constant.RedisKeyConstant;
import com.test.dto.AddImageDTO;
import com.test.dto.DeleteImageDTO;
import com.test.dto.SearchRequest;
import com.test.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/11/12
 * @desc:
 **/
@Service
@Slf4j
public class ImageAnalysisServiceImpl implements ImageAnalysisService{

    @Autowired
    private TiiaClient tiiaClient;

    @Autowired
    private TencentCloudTiiaProperties tiiaProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void syncImage() {
        while (true) {
            SetOperations<String, Object> operations = redisTemplate.opsForSet();
            Object obj = operations.pop(RedisKeyConstant.PRODUCT_IMAGE_SYNC_CACHE_KEY);
            if (obj == null) {
                log.info("暂未发现任务数据");
                return;
            }
            String pop = obj.toString();
            if (StringUtils.isBlank(pop)) {
                continue;
            }
            DeleteImageDTO deleteImageDTO = new DeleteImageDTO();
            deleteImageDTO.setEntityId(pop);
            try {
                this.deleteImage(Collections.singletonList(deleteImageDTO));
            } catch (Exception e) {
                log.error("删除图片失败,entityId {}",pop);
            }
            // todo 获取数据具体的数据
            String imageUrl="";
            // todo picName 需要全局唯一
            String picName="";

            AddImageDTO addImageDTO = new AddImageDTO();
            addImageDTO.setEntityId(pop);
            addImageDTO.setImgUrl(imageUrl);
            addImageDTO.setPicName(picName);
            try {
                this.uploadImage(Collections.singletonList(addImageDTO));
            } catch (Exception e) {
                log.error("上传图片失败,entityId {}",pop);
            }


        }
    }

    @Override
    public ImageInfo [] analysis(SearchRequest searchRequest) throws IOException, TencentCloudSDKException {
        SearchImageRequest request = new SearchImageRequest();
        request.setGroupId(tiiaProperties.getGroupId());
        // 筛选，对应上传接口 Tags
        //request.setFilter("channelCode=\"" + searchRequest.getChannelCode() + "\"");、
        byte[] bytes = searchRequest.getBytes();
        bytes = ImageUtils.compressSize(bytes,searchRequest.getSuffix(),4096,4096);
        String base64 = Base64.encodeBase64String(bytes);
        request.setImageBase64(base64);
        SearchImageResponse searchImageResponse = tiiaClient.SearchImage(request);
        return searchImageResponse.getImageInfos();
    }

    @Override
    public void deleteImage(List<DeleteImageDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(deleteImageDTO -> {
            List<String> picNameList = deleteImageDTO.getPicName();
            if (CollectionUtils.isEmpty(picNameList)) {
                DeleteImagesRequest request = new DeleteImagesRequest();
                request.setGroupId(tiiaProperties.getGroupId());
                request.setEntityId(deleteImageDTO.getEntityId());
                try {
                    // 腾讯限制qps
                    Thread.sleep(100);
                    tiiaClient.DeleteImages(request);
                } catch (TencentCloudSDKException | InterruptedException e) {
                    log.error("删除图片失败, entityId {} 错误信息 {}", deleteImageDTO.getEntityId(), e.getMessage());
                }
            } else {
                picNameList.stream().forEach(picName -> {
                    DeleteImagesRequest request = new DeleteImagesRequest();
                    request.setGroupId(tiiaProperties.getGroupId());
                    request.setEntityId(deleteImageDTO.getEntityId());
                    request.setPicName(picName);
                    try {
                        Thread.sleep(100);
                        tiiaClient.DeleteImages(request);
                    } catch (TencentCloudSDKException | InterruptedException e) {
                        log.error("删除图片失败, entityId {}, 错误信息 {}", deleteImageDTO.getEntityId(), picName, e.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public void uploadImage(List<AddImageDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(imageDTO -> {
            String imgUrlStr = imageDTO.getImgUrl();
            if (StringUtils.isBlank(imgUrlStr)) {
                // 跳过当前元素
                return;
            }
            CreateImageRequest request = new CreateImageRequest();
            request.setGroupId(tiiaProperties.getGroupId());
            request.setEntityId(imageDTO.getEntityId());
            String imageUrl = imageDTO.getImgUrl();
            // 限制大小
            byte[] bytes = ImageUtils.compress(imageUrl,0.6,1024 * 5);
            String imageFormat = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
            // 限制分辨率
            bytes = ImageUtils.compressSize(bytes,imageFormat,4096,4096);
            request.setImageBase64(new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8));
            //JSONObject tagJson = new JSONObject();
            //tagJson.put("code","搜索字段");
            //request.setTags(JSONObject.toJSONString(tagJson));
            request.setPicName(imageDTO.getPicName());
            try {
                tiiaClient.CreateImage(request);
            } catch (TencentCloudSDKException e) {
                log.error("图像上传失败 error:{}", e.getMessage());
            }
        });
    }
}
