package com.shichi.core.doc.style;

import org.apache.poi.xwpf.usermodel.XWPFTable;

public class TableBordersStrategy {
    public TableStyle.TableBorders generate(TableStyle.TableBordersEnum bordersEnum) {
        switch(bordersEnum) {
            case BORDER_TOP -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case BORDER_LEFT -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case BORDER_RIGHT -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case BORDER_BOTTOM -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case BORDER_BETWEEN -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case INSIDE_H_BORDER -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case INSIDE_V_BORDER -> {
                return new TableStyle.TableBorders(XWPFTable.XWPFBorderType.NONE, 1, 0, "000000");
            }
            case default -> {
                return null;
            }
        }

    }

}
