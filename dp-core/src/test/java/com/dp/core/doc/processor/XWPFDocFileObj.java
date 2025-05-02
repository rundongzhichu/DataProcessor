package com.dp.core.doc.processor;

import com.dp.core.constant.DocType;
import com.dp.core.doc.anno.Doc;
import com.dp.core.doc.anno.Paragraph;
import com.dp.core.doc.anno.Picture;
import com.dp.core.doc.anno.Table;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

@Doc(type = DocType.DOCX)
public class XWPFDocFileObj {

    @Paragraph(fontSize = 30)
    private String text = "Hello Wold";

    @Table()
    private XWPFTableObj xwpfTableObj = new XWPFTableObj();

    @Paragraph
    @Picture(url = "/Users/sunshoucai/IdeaProjects/DataProcessor/dp-core/src/test/java/com/dp/core/doc/processor/buyipbird.png",
            type =  XWPFDocument.PICTURE_TYPE_PNG)
    private String pngDescribe = "下面是一张图片！\n";

}
