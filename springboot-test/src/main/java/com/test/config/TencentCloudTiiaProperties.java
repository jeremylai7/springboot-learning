package com.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: laizc
 * @date: 2024/10/23 15:13
 * @desc:
 */
@Data
@Configuration
@ConfigurationProperties("tencentcloud.tiia")
public class TencentCloudTiiaProperties {

    private String secretId;

    private String secretKey;

    private String endpoint = "tiia.tencentcloudapi.com";

    private String region = "ap-guangzhou";

    private String groupId;

}
