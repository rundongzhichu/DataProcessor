package com.shichi.core.excel.transform;

import org.apache.poi.ss.usermodel.Cell;

public interface ValueTransform<JT, CT> {

    <JT> JT transformToJavaType(Class<JT> jt, Class<CT> ct, Cell cell);

    <JT> JT transformToCellType(Class<JT> jt, Class<CT> ct, Object o);

}
