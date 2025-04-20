package com.shichi.core.doc.processor;

import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.anno.Rows;

import java.util.ArrayList;
import java.util.List;

public class XWPFTableObj {

    @Row
    private XWPFTableRowObj xwpfTableRowObj = new XWPFTableRowObj();

    @Rows
    private List<XWPFTableRowObj> xwpfTableRowObjs = new ArrayList<>() {
        {
            add(xwpfTableRowObj);
            add(xwpfTableRowObj);
        }
    };

}
