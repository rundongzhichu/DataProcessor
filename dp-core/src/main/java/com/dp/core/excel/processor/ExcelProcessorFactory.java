package com.dp.core.excel.processor;

import com.dp.core.excel.processor.api.ExcelFileProcessor;
import com.dp.core.excel.resolver.ExcelResolver;
import com.dp.core.excel.anno.Excel;
import com.dp.core.utils.ReflectUtils;

public class ExcelProcessorFactory {

    public static <E> ExcelFileProcessor<E> getProcessor(E e) {
        ExcelResolver excelResolver = new ExcelResolver(ReflectUtils.getAnnonation(e.getClass(), Excel.class), e);
        return excelResolver.getProcessor();
    }

}
