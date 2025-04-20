package com.dp.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Table {

    String borderTop() default "NONE, 1, 0, 000000";

    String borderBottom() default "NONE, 1, 0, 000000";

    String borderLeft() default "NONE, 1, 0, 000000";

    String borderRight() default "NONE, 1, 0, 000000";

    String borderBetween() default "NONE, 1, 0, 000000";

    /**
     * 内部水平边框
     * @return
     */
    String insideHBorder() default "NONE, 1, 0, 000000";

    /**
     * 内部垂直边框
     * @return
     */
    String insideVBorder() default "NONE, 1, 0, 000000";

}
