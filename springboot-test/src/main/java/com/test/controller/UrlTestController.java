package com.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: laizc
 * @date: 2025/3/6 15:34
 * @desc:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class UrlTestController {

    @GetMapping("/url")
    public void url(String imgUrl, HttpServletResponse response) throws IOException {
        // 有问题图片 https://xhzx-platform-dev.oss-cn-shenzhen.aliyuncs.com/202305100256131704281.jpg
        // 没问题的图片 https://xhzx-platform-dev.oss-cn-shenzhen.aliyuncs.com/11110217180.png
        URL url = null;
        try {
            url = new URL(imgUrl);
        } catch (MalformedURLException e) {
            log.info("get url error,message {}" + e.getMessage());
            throw new RuntimeException(e);
        }
        URLConnection con = null;
        try {
            con = url.openConnection();
        } catch (IOException e) {
            log.info("open Connection error,message {}" + e.getMessage());
            throw new RuntimeException(e);
        }
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        InputStream is;
        // 输入流
        try {
            is = con.getInputStream();
        } catch (IOException e) {
            log.info("get InputStream error,message {}" + e.getMessage());
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        String fileType = imgUrl.substring(imgUrl.lastIndexOf(".")).toLowerCase();
        BufferedImage bufferImg = ImageIO.read(is);
        log.info("****************************************************************");
        log.info("图片地址 {} bufferImg {}",imgUrl,bufferImg);
        log.info("****************************************************************");
        if (fileType.equals(".jpg")) {
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
        } else if (fileType.equals(".png")) {
            ImageIO.write(bufferImg, "png", byteArrayOut);
        } else {
            throw new IllegalArgumentException();
        }
        byte[] bytes = byteArrayOut.toByteArray();
        OutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
    }

}
