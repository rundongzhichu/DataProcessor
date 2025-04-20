package com.shichi.core.doc.processor;

import com.shichi.core.constant.DocType;
import com.shichi.core.doc.anno.Doc;
import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.anno.Table;
import com.shichi.core.doc.style.TableBordersStrategy;

@Doc(type = DocType.DOCX)
public class XWPFDocFileObj {

    @Paragraph(fontSize = 30)
    private String text = "Hello Wold";

    @Table(borderTop = TableBordersStrategy.class)
    private XWPFTableObj xwpfTableObj = new XWPFTableObj();

}
