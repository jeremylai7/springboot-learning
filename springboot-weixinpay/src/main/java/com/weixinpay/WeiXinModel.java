package com.weixinpay;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: laizc
 * @Date: created in 2022-08-29
 */
@Component
@ConfigurationProperties(prefix = "weixin")
@Getter
@Setter
public class WeiXinModel {

    /**
     * 获取用户open地址
     */
    private String url;

    /**
     * 微信appid
     */
    private String appId;

    /**
     * 微信密钥
     */
    private String secretKey;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 统一下单url
     */
    private String unifiedOrderUrl;


}
