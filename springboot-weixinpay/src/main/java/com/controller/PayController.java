package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.weixinpay.WeiXinModel;
import com.weixinpayutil.IpUtils;
import com.weixinpayutil.WXPayUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: laizc
 * @Date: created in 2022-08-29
 */
@RestController
public class PayController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeiXinModel weiXinModel;

    @GetMapping("/prepay")
    public Map<String,String> prepay(String wxcode, String money, HttpServletRequest request) throws Exception {
        // 1、获取小程序的 openid
        String openId = getOppenId(wxcode);
        if (StringUtils.isBlank(openId)) {
            throw new Exception("openid 无法为null");
        }
        // 2、统一下单,获取 prepayId
        String prepayId = unifiedOrder(openId,request,money);
        //3、再次签名
        Map<String,String> map= new LinkedHashMap<>();
        map.put("appId",weiXinModel.getAppId());
        map.put("timeStamp",String.valueOf(WXPayUtil.getCurrentTimestamp()));
        map.put("nonceStr",WXPayUtil.generateNonceStr());
        map.put("package","prepay_id="+prepayId);
        map.put("signType","MD5");
        try {
            String paySign =  WXPayUtil.generateSignature(map,"L1mKkZtFmJiiHO5fIMnbnwDIZifGcoyE");
            map.put("paySign",paySign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 统一下单，获取 prepay_id
     * @param openId
     * @param request
     * @param money
     * @return
     */
    private String unifiedOrder(String openId, HttpServletRequest request, String money) {
        String formData = getFormData(openId,request,money);
        String result = restTemplate.postForObject(weiXinModel.getUnifiedOrderUrl(),formData, String.class);
        try {
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap.get("prepay_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getFormData(String openId, HttpServletRequest request, String money) {
        String nonceStr = WXPayUtil.generateNonceStr();
        String notifyUrl = "http://www.jeremy7.cn/news";
        String createIp = IpUtils.getIpAddr(request);
        String tradeNo = WXPayUtil.getRandomOrderId();//商户订单号
        String body = "商品名称";
        Map<String, String> packageParams = new LinkedHashMap<>();
        packageParams.put("appid", weiXinModel.getAppId());
        packageParams.put("body",body);
        packageParams.put("mch_id", weiXinModel.getMchId());
        packageParams.put("nonce_str",nonceStr);
        packageParams.put("notify_url",notifyUrl);//支付成功后的回调地址
        packageParams.put("openid",openId);
        packageParams.put("out_trade_no", tradeNo);
        packageParams.put("spbill_create_ip",createIp);
        packageParams.put("total_fee", money);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("trade_type", "JSAPI");//支付方式
        String sign="";
        try {
            sign= WXPayUtil.generateSignature(packageParams,weiXinModel.getMchKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formData = "<xml>";
        formData += "<appid>"+ weiXinModel.getAppId()+"</appid>"; //appid
        formData += "<body>" + body+ "</body>";
        formData += "<mch_id>"+ weiXinModel.getMchId()+"</mch_id>"; //商户号
        formData += "<nonce_str>"+nonceStr+"</nonce_str>";
        formData += "<notify_url>"+ notifyUrl +"</notify_url>";
        formData += "<openid>"+openId+"</openid>";
        formData += "<out_trade_no>" + tradeNo + "</out_trade_no>";
        formData += "<spbill_create_ip>"+createIp+"</spbill_create_ip>";
        formData += "<total_fee>" + money + "</total_fee>";
        formData += "<trade_type>"+ "JSAPI"+"</trade_type>";
        formData += "<sign>"+sign+"</sign>";
        formData += "</xml>";
        return formData;

    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    private String getOppenId(String code) {
        if (StringUtils.isNoneEmpty(code)) {
            String url = weiXinModel.getUrl();
            String param = "?appid=" + weiXinModel.getAppId() + "&secret=" + weiXinModel.getSecretKey() +
                    "&js_code=" + code + "&grant_type=authorization_code";
            String result = restTemplate.getForObject(url + param,String.class);
            if (result.length() > 0) {
                JSONObject jsonObj = JSONObject.parseObject(result);
                String openId = jsonObj.getString("openid");
                return openId;
            }
        }
        return null;
    }

}
