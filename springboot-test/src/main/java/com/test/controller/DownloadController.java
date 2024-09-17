package com.test.controller;

import com.alibaba.excel.util.StringUtils;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.*;

/**
 * @author: laizc
 * @date: created in 2024/9/17
 * @desc:
 **/
@RestController
@RequestMapping("/download")
public class DownloadController {

    @GetMapping("")
    public void download() throws IOException, DocumentException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        map.put("contractCode", "contractCode");
        map.put("payMode", "payModeStr");
        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        // 指定FreeMarker模板文件的位置
        String templateDirPath = "classpath*:freemarker/";
        Resource[] resources = resourcePatternResolver.getResources(templateDirPath);
        File loadFile = resources[0].getFile();
        cfg.setDirectoryForTemplateLoading(loadFile);
        ITextRenderer renderer = new ITextRenderer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {

            String ttcPath = "classpath*:font/simsun.ttc";
            Resource[] ttfResouces = resourcePatternResolver.getResources(ttcPath);
            ttcPath = ttfResouces[0].getFile().getPath();
            if (StringUtils.isNotBlank(ttcPath)) {
                // 设置 css中 的字体样式（暂时仅支持宋体和黑体） 必须，不然中文不显示
                renderer.getFontResolver().addFont(ttcPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            // 设置模板的编码格式
            cfg.setEncoding(Locale.CHINA, "UTF-8");
            // 获取模板文件
            String templateName = "customer.ftl";
            Template template = cfg.getTemplate(templateName, "UTF-8");
            StringWriter writer = new StringWriter();

            // 将数据输出到html中
            template.process(map, writer);
            writer.flush();

            String html = writer.toString();

            // 把html代码传入渲染器中
            renderer.setDocumentFromString(html);
            String imageBaseUrl = "classpath*:template/images";
            if (StringUtils.isNotBlank(imageBaseUrl)) {
                // 设置模板中的图片路径 （这里的images在resources目录下） 模板中img标签src路径需要相对路径加图片名 如<img src="images/xh.jpg"/>
                renderer.getSharedContext().setBaseURL(imageBaseUrl);
            }
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            outputStream.flush();

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }


        InputStream input = new ByteArrayInputStream(outputStream.toByteArray());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(input);
        PdfStamper stamp = new PdfStamper(reader, output);
        PdfContentByte contentByte;
        int n = reader.getNumberOfPages();
        String ttcPath = ResourceUtils.getURL("classpath:font/simsun.ttc").getPath();
        BaseFont chinese = BaseFont.createFont(ttcPath + ",0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        for (int i = 1; i <= n; i++) {
            contentByte = stamp.getUnderContent(i);
            Rectangle rectangle = reader.getPageSize(i);
            float width = rectangle.getWidth();
            String text = "第 " + i + " 页/共 " + n + " 页";
            contentByte.beginText();
            contentByte.setFontAndSize(chinese, 10);
            contentByte.showTextAligned(Element.ALIGN_CENTER, text, (width / 2) - 6, 15, 0);
            contentByte.endText();
        }
        reader.close();
        stamp.close();
        Date now = new Date();
        //String path = fileUploadPathConfig.getOrderContractTemplatePath() + custId + "/" + DateUtil.getYear(now) + "/" + DateUtil.getMonth(now) + "/" + DateUtil.getDay(now) + "/";
        // 获取当前项目的根目录
        String projectPath = System.getProperty("user.dir");
        File pdfFile = new File(projectPath + "/output.pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
        fileOutputStream.write(output.toByteArray());
    }


}
