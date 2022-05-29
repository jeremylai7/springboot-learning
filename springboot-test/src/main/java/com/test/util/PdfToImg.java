package com.test.util;

import com.lowagie.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author: laizc
 * @date: created in 2022/5/29
 * @desc:
 **/
public class PdfToImg {

    public static void main(String[] args) throws IOException {
        String pdfFilePath = "/Users/jeremy/Documents/gitprogrammer/springboot-learning/springboot-test/src/main/java/com/test/util/pdf.pdf";

        File file = new File(pdfFilePath);
        PDDocument pdDocument;

        String imgPdfPath = file.getParent();
        int dot = file.getName().lastIndexOf('.');
        // 获取图片文件名
        String imagePdfName = file.getName().substring(0, dot);

        pdDocument = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        /* dpi越大转换后越清晰，相对转换速度越慢 */
        PdfReader reader = new PdfReader(pdfFilePath);
        int pages = reader.getNumberOfPages();
        StringBuffer imgFilePath;
        for (int i = 0; i < pages; i++) {
            String imgFilePathPrefix = imgPdfPath + File.separator + imagePdfName;
            imgFilePath = new StringBuffer();
            imgFilePath.append(imgFilePathPrefix);
            imgFilePath.append("_");
            imgFilePath.append((i + 1));
            imgFilePath.append(".png");
            File dstFile = new File(imgFilePath.toString());
            BufferedImage image = renderer.renderImageWithDPI(i, 150);
            ImageIO.write(image, "png", dstFile);
        }





    }
}
