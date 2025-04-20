package com.dp.core.doc.anno;

import org.apache.poi.xwpf.usermodel.TableRowHeightRule;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Row {

    int height() default 5;

    boolean canSplitRow() default  false;

    TableRowHeightRule heightRule() default TableRowHeightRule.AUTO;

    boolean repeatHeader() default false;


}
