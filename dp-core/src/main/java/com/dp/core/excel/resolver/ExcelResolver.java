package com.dp.core.excel.resolver;

import com.dp.core.excel.processor.api.ExcelFileProcessor;
import com.dp.core.excel.anno.Excel;
import com.dp.core.excel.processor.HSSFExcelProcessor;
import com.dp.core.excel.processor.XSSFExcelProcessor;
import org.springframework.util.Assert;

public class ExcelResolver<EA extends Excel, E> {

    private EA ea;

    private E e;

    public ExcelResolver(EA ea, E e) {
        Assert.notNull(ea, "Excel Annotation should not Be Null!");
        this.ea = ea;
        this.e = e;
    }

    public ExcelFileProcessor<EA> getProcessor() {
        switch (ea.type()) {
            case HSSF:
                return new HSSFExcelProcessor(this.e);
            case XSSF:
                return new XSSFExcelProcessor(this.e);
        }
        return null;
    }


}
