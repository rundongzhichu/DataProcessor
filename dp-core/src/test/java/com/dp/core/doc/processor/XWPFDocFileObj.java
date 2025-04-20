package com.dp.core.doc.processor;

import com.dp.core.constant.DocType;
import com.dp.core.doc.anno.Doc;
import com.dp.core.doc.anno.Paragraph;
import com.dp.core.doc.anno.Table;

@Doc(type = DocType.DOCX)
public class XWPFDocFileObj {

    @Paragraph(fontSize = 30)
    private String text = "Hello Wold";

    @Table()
    private XWPFTableObj xwpfTableObj = new XWPFTableObj();

}
