package com.dp.core.excel.processor;

import com.dp.core.excel.resolver.HSSFExcelRowResolver;
import com.dp.core.excel.resolver.HSSFExcelRowsResolver;
import com.dp.core.excel.anno.ECell;
import com.dp.core.excel.anno.ERow;
import com.dp.core.excel.anno.ERows;
import com.dp.core.excel.resolver.HSSFExcelCellResolver;
import com.dp.core.utils.ReflectUtils;
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

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSSFExcelProcessor<E> extends DefaultExcelProcessor<E> {

    Logger logger = LoggerFactory.getLogger(HSSFExcelProcessor.class);

    private HSSFWorkbook workbook;

    private E e;

    public HSSFExcelProcessor(E e) {
        this.e = e;
        workbook = new HSSFWorkbook();
    }

    public HSSFExcelProcessor(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = new HSSFWorkbook(fileInputStream);
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void parseRowModelObjToExcel() {
        parseRowModelObjToExcel(this.e);
    }

    public void parseRowModelObjToExcel(E excel) {
        Field[] fields = excel.getClass().getDeclaredFields();
        try {
            for (Field field :
                    fields) {
                ReflectUtils.setAccessible(field, true);
                ERows eRows = ReflectUtils.getAnnonation(field, ERows.class);
                if(eRows != null) {
                    Object rowsObj = field.get(excel);
                    assert rowsObj instanceof List;
                    parseRowObjsToExcel((List)rowsObj, false);
                }

                ERow eRow = ReflectUtils.getAnnonation(field, ERow.class);
                if(eRow != null) {
                    Object rowObj = field.get(excel);
                    parseRowObjsToExcel(List.of(rowObj), false);
                }
            }
        } catch (IllegalAccessException illegalAccessException) {
            logger.error("Illegal Access!", e);
        }
    }

    public <R> void parseRowObjToExcel(HSSFSheet sheet, int currentRow, R rowObj) {
        HSSFRow row = sheet.createRow(++currentRow);
        Class excelObjClass = rowObj.getClass();
        Field[] fields = excelObjClass.getDeclaredFields();
        for (Field field :
                fields) {
            ECell cell = ReflectUtils.getAnnonation(field, ECell.class);
            if(cell == null) continue;
            HSSFExcelCellResolver hssfExcelCellResolver = new HSSFExcelCellResolver(rowObj, field, cell);
            Object value = hssfExcelCellResolver.getFieldValue();
            HSSFCell hssfCell = row.createCell(hssfExcelCellResolver.getColumnIndex());
            if(value != null) {
                hssfCell.setCellValue(value.toString());
            }
        }
    }

    public <R> void parseRowObjsToExcel(List<R> rowObjs, boolean newSheet) {
        if (CollectionUtils.isEmpty(rowObjs)) throw new NullPointerException("Empty Object List!");
        HSSFSheet sheet = null;
        if(newSheet || workbook.getNumberOfSheets() == 0) {
            sheet = workbook.createSheet();
        } else {
            int activeSheet = workbook.getActiveSheetIndex();
            sheet = workbook.getSheetAt(activeSheet);
        }
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0, size = rowObjs.size(); i < size; i++) {
            parseRowObjToExcel(sheet, lastRowNum, rowObjs.get(i));
        }
    }

    public <R> R parseExcelRowToObj(HSSFSheet sheet, int currentRow, Class<R> rc) throws Exception {
        HSSFRow row = sheet.getRow(currentRow);
        Field[] fields = rc.getDeclaredFields();
        Map<Integer, HSSFExcelCellResolver>  cellFields = new HashMap<>();
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
            logger.error("can not instantiate a instance.", e);
        } catch (IllegalAccessException e) {
            logger.error("can not access the field.", e);
        } catch (InvocationTargetException e) {
            logger.error("can not invoke method.", e);
        } catch (NoSuchMethodException e) {
            logger.error("no such method.", e);
        } catch (Exception e) {
            logger.error("error occurs.", e);
            throw e;
        }
        return r;
    }

    public E parseExcelToObj(Class<E> excelObj) {
        int activeSheet = workbook.getActiveSheetIndex();
        HSSFSheet sheet = workbook.getSheetAt(activeSheet);

        int currentRow = 0;
        E excel = null;
        try {
            Field[] fields = excelObj.getDeclaredFields();
            excel = excelObj.getConstructor().newInstance();
            for (Field field :
                    fields) {
                ReflectUtils.setAccessible(field, true);
                ERows eRows = ReflectUtils.getAnnonation(field, ERows.class);
                if(eRows != null) {
                    HSSFExcelRowsResolver hssfExcelRowsResolver = new HSSFExcelRowsResolver(excel, field, eRows);
                    Class rowType = eRows.rowType();
                    List rows = parseExcelRowsToObjs(sheet, currentRow, rowType);
                    hssfExcelRowsResolver.setFieldValue(excel, rows);
                    currentRow += rows.size();
                }

                ERow eRow = ReflectUtils.getAnnonation(field, ERow.class);
                if(eRow != null) {
                    HSSFExcelRowResolver hssfExcelRowResolver = new HSSFExcelRowResolver(excel, field, eRow);
                    Object row = parseExcelRowToObj(sheet, currentRow, field.getType());
                    hssfExcelRowResolver.setFieldValue(excel, row);
                    currentRow++;
                }
            }
        } catch (InstantiationException e) {
            logger.error("can not instantiate a instance.", e);
        } catch (IllegalAccessException e) {
            logger.error("can not access the field.", e);
        } catch (InvocationTargetException e) {
            logger.error("can not invoke method.", e);
        } catch (NoSuchMethodException e) {
            logger.error("no such method.", e);
        } catch (Exception e) {
            logger.error("error occurs.", e);
        }
        return excel;
    }

    public <R> List<R> parseExcelRowsToObjs(HSSFSheet sheet, int curentRow, Class<R> rc) {
        int lastRowNum = sheet.getLastRowNum();
        List<R> objs = new ArrayList<>();
        try {
            for (int i = curentRow; i <= lastRowNum; i++) {
                objs.add(parseExcelRowToObj(sheet, i, rc));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return objs;
    }

    public <R> List<R> parseExcelRowsToObjs(Class<R> rc) {
        int activeSheet = workbook.getActiveSheetIndex();
        HSSFSheet sheet = workbook.getSheetAt(activeSheet);

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        List<R> objs = new ArrayList<>();
        try {
            for (int i = firstRowNum; i <= lastRowNum; i++) {
                objs.add(parseExcelRowToObj(sheet, i, rc));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return objs;
    }

    public void saveToFile(String filePath) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            logger.error("Can not find the file!", e);
        } catch (IOException e) {
            logger.error("IO Exception occurs!", e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ioException) {
                logger.error("IO Exception occurs when close file outputstream!", e);
            }
        }
    }

    private boolean invalidRow(Row row) {
        try {
            if (row == null) return true;
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();
            if (firstCellNum < 0 && lastCellNum < 0) return true;
            if (firstCellNum != 0) {
                for (short i = firstCellNum; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = cell.getStringCellValue();
                    if (StringUtils.isBlank(cellValue)) return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("an error occurs.", e);
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
