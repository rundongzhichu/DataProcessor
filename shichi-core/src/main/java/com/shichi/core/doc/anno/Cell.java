package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cell {

    /**
     * @return row num of the data, default value starts with o0
     */
    int row() default 0;

    int col() default 0;

}
