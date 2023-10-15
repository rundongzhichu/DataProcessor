package com.shichi.core.excel.anno;

import com.shichi.core.excel.constant.ExcelType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Excel {
    ExcelType type() default ExcelType.XSSF;
}
