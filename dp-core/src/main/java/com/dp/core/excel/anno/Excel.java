package com.dp.core.excel.anno;

import com.dp.core.constant.ExcelType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE, ElementType.FIELD})
public @interface Excel {
    ExcelType type() default ExcelType.XSSF;
}
