package com.dp.core.doc.anno;

import org.apache.poi.wp.usermodel.HeaderFooterType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Footer {

    HeaderFooterType relation() default HeaderFooterType.DEFAULT;

}