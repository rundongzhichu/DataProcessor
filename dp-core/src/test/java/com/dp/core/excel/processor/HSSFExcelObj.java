package com.dp.core.excel.processor;

import com.dp.core.excel.anno.ERow;
import com.dp.core.excel.anno.ERows;
import com.dp.core.excel.anno.Excel;
import com.dp.core.constant.ExcelType;

import java.util.List;

@Excel(type = ExcelType.HSSF)
public class HSSFExcelObj {

    @ERow
    private HSSFRowObj hssfRowObj;

    @ERows(rowType = HSSFRowObj.class)
    private List<HSSFRowObj> hssfRowObjs;

    public HSSFRowObj getHssfRowObj() {
        return hssfRowObj;
    }

    public void setHssfRowObj(HSSFRowObj hssfRowObj) {
        this.hssfRowObj = hssfRowObj;
    }

    public List<HSSFRowObj> getHssfRowObjs() {
        return hssfRowObjs;
    }

    public void setHssfRowObjs(List<HSSFRowObj> hssfRowObjs) {
        this.hssfRowObjs = hssfRowObjs;
    }

}
