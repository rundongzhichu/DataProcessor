package com.dp.core.doc.anno;

import com.dp.core.constant.DocType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.TYPE, ElementType.FIELD})
public @interface Doc {

    DocType type() default DocType.DOCX;

    String path() default "";

    String outputPath() default "";

}
