package com.shichi.core.excel.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class HSSFExcelProcessorTests {

    private HSSFExcelObj hssfExcelObj;

    @BeforeEach
    public void setUp() {
        hssfExcelObj = new HSSFExcelObj();
        HSSFRowObj hssfRowObj = new HSSFRowObj();
        hssfRowObj.setColumn1("张三");
        hssfRowObj.setColumn2("上海浦东");
        hssfRowObj.setColumn3("9090980");
        hssfRowObj.setColumn4("12345@163.com");
        hssfExcelObj.setHssfRowObj(hssfRowObj);
        hssfExcelObj.setHssfRowObjs(List.of(hssfRowObj));
    }

    @Test
    public void testHssfExcelProcessor() {
        HSSFExcelProcessor hssfExcelProcessor = new HSSFExcelProcessor<>(hssfExcelObj);
        hssfExcelProcessor.parseRowModelObjToHSSFExcel();
        hssfExcelProcessor.saveToFile("test.xlsx");
    }

    @Test
    public void testParseExcelToObject() {
        HSSFExcelProcessor hssfExcelProcessor = null;
        try {
            hssfExcelProcessor = new HSSFExcelProcessor<>("test.xlsx");
            List<HSSFRowObj> rowObjs = hssfExcelProcessor.parseExcelRowsToObjs(HSSFRowObj.class);
            for (HSSFRowObj row :
                    rowObjs) {
                System.out.println(row.getColumn2());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
