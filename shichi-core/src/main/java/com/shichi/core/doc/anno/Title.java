package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Title {
//
//    public static void createMultiLevelHeading(String filePath) {
//        try (FileInputStream fis = new FileInputStream(filePath);
//             XWPFDocument doc = new XWPFDocument(fis)) {
//            // 获取第一段（默认为空）
//            XWPFParagraph paragraph = doc.getParagraphs().get(0);
//            if (paragraph == null) {
//                paragraph = doc.createParagraph();
//            }
//
//            // 设置标题样式
//            CTPPr ppr = paragraph.getCTP().addNewPPr();
//            CTOutlineLvl outlineLvl = ppr.addNewOutlineLvl();
//            outlineLvl.setVal(BigInteger.valueOf(1)); // 设置标题级别为1
//
//            // 添加内容
//            XWPFRun run = paragraph.createRun();
//            run.setText("这是一个多级标题");
//
//            // 保存修改后的文件
//            OutputStream out = new FileOutputStream(new File(filePath + ".modified"));
//            doc.write(out);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
