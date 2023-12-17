package com.shichi.core.doc.anno;

import org.apache.poi.wp.usermodel.HeaderFooterType;

public @interface Header {

    HeaderFooterType relation() default HeaderFooterType.DEFAULT;

}