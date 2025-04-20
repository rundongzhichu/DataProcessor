package com.dp.core.doc.anno;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;

import java.lang.annotation.*;

/**
 * @author shoucai
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Paragraph {
    Borders borderTop() default Borders.NONE;

    Borders borderBottom() default Borders.NONE;

    Borders borderLeft() default Borders.NONE;

    Borders borderRight() default Borders.NONE;

    Borders borderBetween() default Borders.NONE;

    boolean bold() default false;

    boolean italic() default false;

    boolean strikeThrough() default false;

    int textPosition() default 10;

    int fontSize() default 20;

    VerticalAlign verticalAlign() default VerticalAlign.BASELINE;

    ParagraphAlignment paragraphAlignment() default ParagraphAlignment.CENTER;

}
