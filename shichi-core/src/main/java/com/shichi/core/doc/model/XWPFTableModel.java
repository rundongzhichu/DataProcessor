package com.shichi.core.doc.model;

import java.util.List;

public class XWPFTableModel {

    /**
     * 表格有多少行
     */
    private int rowCnt;

    /**
     * 表格有多少列
     */
    private int colCnt;

    /**
     * 标识表格的长宽是否固定
     */
    private boolean fixed;

    /**
     * 是否优先采用注解提供的配置
     */
    private boolean annoFirst = false;


    /**
     * 数据行的配置
     */
    private List<XWPFTableRowModel> rows;

    public int getRowCnt() {
        return rowCnt;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    public int getColCnt() {
        return colCnt;
    }

    public void setColCnt(int colCnt) {
        this.colCnt = colCnt;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isAnnoFirst() {
        return annoFirst;
    }

    public void setAnnoFirst(boolean annoFirst) {
        this.annoFirst = annoFirst;
    }
}
