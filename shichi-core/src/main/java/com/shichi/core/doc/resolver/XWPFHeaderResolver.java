package com.shichi.core.doc.resolver;

public class XWPFHeaderResolver {

    // 创建新的Word文档对象
    XWPFDocument document = new XWPFDocument();

    // 获取文档的属性对象
    CTDocProps coreProperties = document.getProperties().getCoreProperties();
coreProperties.setTitle("标题"); // 设置文档标题
coreProperties.setAuthor("作者名字"); // 设置作者信息
coreProperties.setCreated(new Date()); // 设置创建日期

    // 添加页眉内容
    XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
    XWPFParagraph paragraph = header.createParagraph();
paragraph.createRun().setText("这里是页眉内容");

    // 添加页脚内容
    XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
    XWPFParagraph footPara = footer.createParagraph();
footPara.createRun().setText("这里是页脚内容");

    // 保存生成的Word文档到指定路径
    FileOutputStream outStream = new FileOutputStream("output.docx");
document.write(outStream);
outStream.close();

}
