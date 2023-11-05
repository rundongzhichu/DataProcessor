package com.shichi.core.excel.processor;

import com.shichi.core.excel.anno.ECell;
import com.shichi.core.excel.resolver.HSSFExcelCellResolver;
import com.shichi.core.utils.ReflectUtils;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSSFExcelProcessor<R> extends DefaultExcelProcessor<R> {

    Logger logger = LoggerFactory.getLogger(HSSFExcelProcessor.class);

    private HSSFWorkbook workbook;

    public HSSFExcelProcessor() {
        workbook = new HSSFWorkbook();
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void parseRowObjToExcel(HSSFSheet sheet, int currentRow, R excelObj) {
        HSSFRow row = sheet.createRow(++currentRow);
        Class excelObjClass = excelObj.getClass();
        Field[] fields = excelObjClass.getDeclaredFields();
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

    public void parseRowObjsToExcel(List<R> excelObjs, boolean newSheet) {
        if (CollectionUtils.isEmpty(excelObjs)) throw new NullPointerException("Empty Object List!");
        int activeSheet = workbook.getActiveSheetIndex();
        HSSFSheet sheet = workbook.getSheetAt(activeSheet);
        if(newSheet) {
            sheet = workbook.createSheet();
        }
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0, size = excelObjs.size(); i < size; i++) {
            parseRowObjToExcel(sheet, lastRowNum, excelObjs.get(i));
        }
    }

    public R parseExcelRowToObj(HSSFSheet sheet, int currentRow, Class<R> rc) {
        HSSFRow row = sheet.getRow(currentRow);
        Field[] fields = rc.getDeclaredFields();
        Map<Integer, HSSFExcelCellResolver>  cellFields = new HashMap<>();
        int index = 0;
        for (Field field :
                fields) {
            ECell cell = ReflectUtils.getAnnonation(field, ECell.class);
            if (cell == null) continue;
            HSSFExcelCellResolver hssfExcelCellResolver = new HSSFExcelCellResolver(row, field, cell);
            cellFields.put(cell.index(), hssfExcelCellResolver);
        }

        R r = null;
        try {
            r = rc.getConstructor().newInstance();
            if(row == null || invalidRow(row)) return r;
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();
            for (int i = firstCellNum; i < lastCellNum; i++) {
                HSSFExcelCellResolver hssfExcelCellResolver = cellFields.get(i);
                Cell cell = row.getCell(i);
                String value = valueCheck(cell, hssfExcelCellResolver.getCa().isMust());
                hssfExcelCellResolver.setFieldValue(r, value);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public List<R> parseExcelRowsToObjs(Class<R> rc) {
        int activeSheet = workbook.getActiveSheetIndex();
        HSSFSheet sheet = workbook.getSheetAt(activeSheet);

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        List<R> objs = new ArrayList<>();
        for (int i = firstRowNum; i < lastRowNum; i++) {
            objs.add(parseExcelRowToObj(sheet, i, rc));
        }
        return objs;
    }

    public void saveToFile(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            logger.error("Can not find the file!", e);
        } catch (IOException e) {
            logger.error("IO Exception occurs!", e);
        }
    }

    private boolean invalidRow(Row row) {
        try {
            if (row == null) return false;
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();
            if (firstCellNum < 0 && lastCellNum < 0) return false;
            if (firstCellNum != 0) {
                for (short i = firstCellNum; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = cell.getStringCellValue();
                    if (!StringUtils.isBlank(cellValue)) return true;
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private String valueCheck(Cell cell, Boolean isMust) throws Exception {
        if (cell == null && isMust) {
            throw new Exception(" can not be empty!" );
        }
        if (cell == null) return null;
        String value = cell.getStringCellValue();
        if ((value == null || value.trim().isEmpty()) && isMust) {
            throw new Exception("cell can not be empty!" );
        }
        return value;
    }

}
