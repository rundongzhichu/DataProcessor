package com.dp.core;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class Test {

    public static <XWPFPicture> void main(String[] args) {
        try (XWPFDocument doc = new XWPFDocument()) {
            // 1. 创建段落
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setText("图片测试：");

            // 2. 添加图片
            String imgPath = "/Users/sunshoucai/IdeaProjects/DataProcessor/dp-core/src/test/java/com/dp/core/doc/processor/buyipbird.png"; // 改为实际路径
            try (InputStream is = new FileInputStream(imgPath)) {
                XWPFPicture picture = (XWPFPicture) run.addPicture(
                        is,
                        XWPFDocument.PICTURE_TYPE_JPEG,
                        imgPath,
                        Units.toEMU(300),
                        Units.toEMU(200)
                );
            }

            // 3. 验证图片数据
            List<XWPFPictureData> pics = doc.getAllPictures();
            System.out.println("嵌入图片数量: " + pics.size());
            pics.forEach(pic -> System.out.println(
                    "图片类型: " + pic.getPictureType() +
                            ", 大小: " + pic.getData().length + " bytes"
            ));

            // 4. 保存文档
            try (FileOutputStream out = new FileOutputStream("test_output.docx")) {
                doc.write(out);
                System.out.println("文档已保存");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
