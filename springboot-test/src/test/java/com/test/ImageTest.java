package com.test;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tiia.v20190529.models.CreateImageRequest;
import com.tencentcloudapi.tiia.v20190529.models.CreateImageResponse;
import com.tencentcloudapi.tiia.v20190529.models.SearchImageRequest;
import com.tencentcloudapi.tiia.v20190529.models.SearchImageResponse;
import org.junit.Test;
import org.junit.Test;

/**
 * @author: laizc
 * @date: created in 2024/10/23
 * @desc:
 **/
public class ImageTest {



    @Test
    public void addImage() throws TencentCloudSDKException {
        // appid 1311435806
        Credential cred = new Credential("xxxxxxx", "xxxxxx");
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("tiia.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 初始化 TiiaClient
        TiiaClient client = new TiiaClient(cred, "ap-beijing", clientProfile);  // 选择地区
        CreateImageRequest request = new CreateImageRequest();
        request.setGroupId("test1");
        request.setEntityId("test_entity12");
        request.setPicName("图片唯一名称112");
        String imageUrl = "https://unify-prod-1321732912.cos.ap-guangzhou.myqcloud.com/1101040000973.jpg";
        request.setImageUrl(imageUrl);
        request.setCustomContent("品牌：34444");
        JSONObject tagJson = new JSONObject();
        tagJson.put("channelCode",2333);

        request.setTags(JSONObject.toJSONString(tagJson));

        CreateImageResponse response = client.CreateImage(request);
        System.out.println(response);

        // 创建图像搜索请求
//        ImageSearchRequest req = new ImageSearchRequest();
//        req.setImageBase64("your-image-base64");  // 设置图像的 Base64 编码
//
//        // 调用图像搜索接口
//        ImageSearchResponse resp = client.ImageSearch(req);

    }

    @Test
    public void searchImage() throws TencentCloudSDKException {
        // appid 1311435806
        Credential cred = new Credential("xxxx", "xxxxxx");
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("tiia.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 初始化 TiiaClient
        TiiaClient client = new TiiaClient(cred, "ap-beijing", clientProfile);  // 选择地区
        SearchImageRequest request = new SearchImageRequest();
        request.setGroupId("test1");
        request.setFilter("channelCode=2333");
        // 不匹配
        //String url = "https://unify-prod-1321732912.cos.ap-guangzhou.myqcloud.com/1101040000973.jpg";

        String url = "https://unify-prod-1321732912.cos.ap-guangzhou.myqcloud.com/1101040000973.jpg";


        request.setImageUrl(url);


        SearchImageResponse response = client.SearchImage(request);
        System.out.println(response);
    }
}
