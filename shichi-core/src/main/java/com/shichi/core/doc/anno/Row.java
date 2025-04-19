package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Row {

    /**
     * @return row num of the data, default value starts with o0
     */
    int row() default 0;

}
