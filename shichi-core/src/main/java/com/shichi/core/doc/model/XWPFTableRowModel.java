package com.shichi.core.doc.model;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Cells;

import java.util.List;

public class XWPFTableRowModel {
    /**
     * 表格有多少列
     */
    private int colCnt;


    /**
     * 定义数据行的配置
     */
    @Cells
    private List<XWPFTableCellModel> cols;


    /**
     * 是否优先采用注解提供的配置
     */
    private boolean annoFirst = false;
}
