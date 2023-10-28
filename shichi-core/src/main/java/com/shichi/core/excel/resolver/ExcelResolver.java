package com.shichi.core.excel.resolver;

import com.shichi.core.excel.anno.Excel;
import com.shichi.core.excel.processor.HSSFExcelProcessor;
import com.shichi.core.excel.processor.XSSFExcelProcessor;
import com.shichi.core.excel.processor.api.ExcelFileProcessor;
import org.springframework.util.Assert;

public class ExcelResolver<EA extends Excel> {

    private EA e;

    public ExcelResolver(EA e) {
        Assert.notNull(e, "Excel Annotation should not Be Null!");
        this.e = e;
    }

    public ExcelFileProcessor<EA> getProcessor() {
        switch (e.type()) {
            case HSSF:
                return new HSSFExcelProcessor<>();
            case XSSF:
                return new XSSFExcelProcessor<>();
        }
        return null;
    }


}
