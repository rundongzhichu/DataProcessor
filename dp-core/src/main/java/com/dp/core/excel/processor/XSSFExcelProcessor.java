package com.dp.core.excel.processor;

import com.dp.core.excel.processor.api.ExcelFileProcessor;

public class XSSFExcelProcessor<E> implements ExcelFileProcessor<E> {

    private E e;

    public XSSFExcelProcessor(E e) {
        this.e = e;
    }


}
