package com.shichi.core.excel.processor;

import com.shichi.core.excel.anno.Excel;
import com.shichi.core.excel.processor.api.ExcelFileProcessor;
import com.shichi.core.excel.resolver.ExcelResolver;
import com.shichi.core.utils.ReflectUtils;

public class ExcelProcessorFactory {

    public static <E> ExcelFileProcessor<E> getProcessor(E e) {
        ExcelResolver excelResolver = new ExcelResolver(ReflectUtils.getAnnonation(e.getClass(), Excel.class), e);
        return excelResolver.getProcessor();
    }

}
