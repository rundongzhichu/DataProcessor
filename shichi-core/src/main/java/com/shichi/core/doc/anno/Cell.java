package com.shichi.core.doc.anno;

import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.FIELD, ElementType.TYPE})
public @interface Cell {
    TableWidthType widthType() default TableWidthType.AUTO;

    String width() default "5";

    XWPFTableCell.XWPFVertAlign verticalAlignment() default XWPFTableCell.XWPFVertAlign.CENTER;

    String color() default "FFFFFF";

}
