package com.shichi.core.doc.processor;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Cells;
import com.shichi.core.doc.anno.Row;

import java.util.ArrayList;
import java.util.List;

@Row
public class XWPFTableRowObj {

    @Cell
    private XWPFTableCellObj c1 = new XWPFTableCellObj();

    @Cell
    private XWPFTableCellObj c2 = new XWPFTableCellObj();

    @Cells
    private List<XWPFTableCellObj> c3 = new ArrayList<>() {
        {
            add(c1);
            add(c2);
        }
    };


}
