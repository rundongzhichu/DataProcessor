package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.FIELD})
public @interface Cell {

    int col() default 0;

}
