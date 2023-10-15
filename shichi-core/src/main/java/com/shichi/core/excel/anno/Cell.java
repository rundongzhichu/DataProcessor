package com.shichi.core.excel.anno;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cell {
    String name();

    int width() default 6000;

    int index() default -1;

    boolean isMust() default false;

}
