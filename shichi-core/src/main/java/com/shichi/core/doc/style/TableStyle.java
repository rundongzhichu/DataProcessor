package com.shichi.core.doc.style;

import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.reflect.Method;
import java.util.function.Function;

public class TableStyle {

    public static class TableBorders {

        public XWPFTable.XWPFBorderType type;

        public int size;
        public int spac;
        public String rgbColor;

        public TableBorders(XWPFTable.XWPFBorderType type, int size, int spac, String rgbColor) {
            this.type = type;
            this.size = size;
            this.spac = spac;
            this.rgbColor = rgbColor;
        }
    }

    public enum TableBordersEnum {
        BORDER_TOP,
        BORDER_BOTTOM,
        BORDER_LEFT,
        BORDER_RIGHT,
        BORDER_BETWEEN,
        INSIDE_H_BORDER,
        INSIDE_V_BORDER
        ;
    }

}
