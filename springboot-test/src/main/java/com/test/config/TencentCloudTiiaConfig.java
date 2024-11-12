package com.test.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: laizc
 * @date: 2024/10/23 15:10
 * @desc: 图像搜索
 */
@Configuration
@ConditionalOnClass(TencentCloudTiiaProperties.class)
public class TencentCloudTiiaConfig {

    @Bean
    public TiiaClient tiiaClient(TencentCloudTiiaProperties properties) {
        Credential cred = new Credential(properties.getSecretId(), properties.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(properties.getEndpoint());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        TiiaClient client = new TiiaClient(cred, properties.getRegion(), clientProfile);
        return client;
    }
}
