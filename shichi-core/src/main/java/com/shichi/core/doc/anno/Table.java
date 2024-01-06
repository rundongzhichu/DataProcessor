package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Table {

    boolean fixed() default false;

    int row() default 1;

    int col() default 1;

}
