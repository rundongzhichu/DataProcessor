package com.dp.core.doc.anno;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Picture {

    String url();

    String title() default "";

    int width() default 200;

    int height() default 150;

    /**
     * 图片类型
     *
     * @return
     */
    int type() default XWPFDocument.PICTURE_TYPE_PNG;

}
