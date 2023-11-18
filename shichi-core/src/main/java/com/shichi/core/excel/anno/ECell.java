package com.shichi.core.excel.anno;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ECell {
    String name();

    int width() default 6000;

    int index() default -1;

    boolean isMust() default false;



}
