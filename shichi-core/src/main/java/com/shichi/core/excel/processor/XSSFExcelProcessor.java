package com.shichi.core.excel.processor;

import com.shichi.core.excel.processor.api.ExcelFileProcessor;

public class XSSFExcelProcessor<E> implements ExcelFileProcessor<E> {

    private E e;

    public XSSFExcelProcessor(E e) {
        this.e = e;
    }


}
