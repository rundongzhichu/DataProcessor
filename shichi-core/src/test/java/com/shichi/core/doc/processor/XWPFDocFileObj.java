package com.shichi.core.doc.processor;

import com.shichi.core.constant.DocType;
import com.shichi.core.doc.anno.Doc;
import com.shichi.core.doc.anno.Paragraph;

@Doc(type = DocType.DOCX)
public class XWPFDocFileObj {

    @Paragraph(fontSize = 30)
    private String text = "Hello Wold";

}
