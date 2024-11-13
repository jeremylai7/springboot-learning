package com.test.util;

import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author: laizc
 * @date: 2024/11/13
 * @desc:
 */
@Slf4j
public class ImageUtils {

    /**
     * 图片压缩
     * @param url             图片url
     * @param scale           压缩比例
     * @param targetSizeByte  压缩后大小 KB
     * @return
     */
    public static byte[] compress(String url, double scale, long targetSizeByte) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        long targetSizeKB = targetSizeByte * 1024;
        try {
            URL u = new URL(url);
            double quality = 0.8;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
            do {
                Thumbnails.of(u).scale(scale) // 压缩比例
                        .outputQuality(quality) // 图片质量
                        .toOutputStream(outputStream);
                long fileSize = outputStream.size();
                if (fileSize <= targetSizeKB) {
                    return outputStream.toByteArray();
                }
                outputStream.reset();
                if (quality > 0.1) {
                    quality -= 0.1;
                } else {
                    scale -= 0.1;
                }
            } while (quality > 0 || scale > 0);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 图片压缩
     * @param imageData
     * @param width           宽度
     * @param height          高度
     * @return
     */
    public static byte[] compressSize(byte[] imageData,String outputFormat,int width,int height) {
        if (imageData == null) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();
            if (imageWidth <= width && imageHeight <= height) {
                return imageData;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
            Thumbnails.of(bufferedImage)
                    .size(width,height)
                    .outputFormat(outputFormat)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
