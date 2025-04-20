package com.shichi.core.doc.anno;

import com.shichi.core.doc.style.TableBordersStrategy;
import com.shichi.core.doc.style.TableStyle;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Table {

    boolean fixed() default false;

    int row() default 1;

    int col() default 1;

    Class<? extends TableBordersStrategy> borderTop() default TableBordersStrategy.class;

    Class<? extends TableBordersStrategy> borderBottom() default TableBordersStrategy.class;

    Class<? extends TableBordersStrategy> borderLeft() default TableBordersStrategy.class;

    Class<? extends TableBordersStrategy> borderRight() default TableBordersStrategy.class;

    Class<? extends TableBordersStrategy> borderBetween() default TableBordersStrategy.class;

    /**
     * 内部水平边框
     * @return
     */
    Class<? extends TableBordersStrategy> insideHBorder() default TableBordersStrategy.class;

    /**
     * 内部垂直边框
     * @return
     */
    Class<? extends TableBordersStrategy> insideVBorder() default TableBordersStrategy.class;

}
