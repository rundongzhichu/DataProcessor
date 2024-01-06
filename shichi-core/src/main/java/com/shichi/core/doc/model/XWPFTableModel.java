package com.shichi.core.doc.model;

public class XWPFTableModel {

    private int row;

    private int col;

    private boolean fixed;

    private boolean annoFirst;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
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
