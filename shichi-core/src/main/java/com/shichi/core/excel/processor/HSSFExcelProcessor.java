package com.shichi.core.excel.processor;

import com.shichi.core.excel.anno.ECell;
import com.shichi.core.excel.resolver.HSSFExcelCellResolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Field;

public class HSSFExcelProcessor<E> extends DefaultExcelProcessor<E> {

    private HSSFWorkbook workbook;

    private int currentRow = 0;

    public HSSFExcelProcessor() {
        workbook = new HSSFWorkbook();
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void parseRowObj(HSSFSheet sheet, E excelObj) {
        HSSFRow row = sheet.createRow(++currentRow);
        Class excelObjClass = excelObj.getClass();
        Field[] fields = excelObjClass.getDeclaredFields();
        int curColIndex = 0;
        for (Field field :
                fields) {
            ECell cell = ReflectUtils.getAnnonation(field, ECell.class);
            if(cell == null) continue;
            HSSFExcelCellResolver hssfExcelCellResolver = new HSSFExcelCellResolver(excelObj, field, cell);
            Object value = hssfExcelCellResolver.getFieldValue();
            HSSFCell hssfCell = row.createCell(hssfExcelCellResolver.getColumnIndex());
            if(value != null) {
                hssfCell.setCellValue(value.toString());
            }
        }
    }


}
