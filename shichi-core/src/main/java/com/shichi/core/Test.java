//package com.shichi.core;
//
//import io.micrometer.common.util.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class Test {
//
//    public static HSSFWorkbook toExcel(List objList) {
//        if (CollectionUtils.isEmpty(objList)) throw new NullPointerException("Empty Object List!");
//
//        Class aClass = objList.get(0).getClass();
//        Field[] fields = aClass.getDeclaredFields();
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet();
//
//        for (int i = 0; i < objList.size(); i++) {
//            HSSFRow row = sheet.createRow(i + 1);
//            HSSFRow topRow = null;
//            if (i == 0) topRow = sheet.createRow(0);
//            for (Field field : fields) {
//                Excel excel = field.getAnnotation(Excel.class);
//                if (excel == null) continue;
//                HSSFCell cell = row.createCell(excel.index());
//                String startName = field.getName().substring(0, 1);
//                String endName = field.getName().substring(1, field.getName().length());
//
//                String methodName = null;
//                if(field.getType().getName().toLowerCase(Locale.ROOT).equals("boolean")) {
//                    methodName = new StringBuffer(IS).append(startName.toUpperCase()).append(endName).toString();
//                } else {
//                    methodName = new StringBuffer(GET).append(startName.toUpperCase()).append(endName).toString();
//                }
//                try {
//                    Method method = aClass.getMethod(methodName);
//                    Object invoke = method.invoke(objList.get(i));
//                    if (invoke == null) continue;
//                    cell.setCellValue(invoke.toString());
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                if (topRow == null) continue;
//                HSSFCell topRowCell = topRow.createCell(excel.index());
//                topRowCell.setCellValue(excel.name());
//                sheet.setColumnWidth(excel.index(), excel.width());
//            }
//        }
//        return workbook;
//    }
//
//    public static <T> List<T> excelFileToObject(MultipartFile file, Class<T> c) throws Exception {
//        Map<Integer, String> sethodMap = new HashMap<>();
//        Map<Integer, String> startRowNameMap = new HashMap<>();
//        Map<Integer, Boolean> fieldIsMustMap = new HashMap<>();
//
//        Field[] fields = c.getDeclaredFields();
//        for (Field field : fields) {
//            Excel excel = field.getAnnotation(Excel.class);
//            if (excel == null) continue;
//            String startName = field.getName().substring(0, 1);
//            String endName = field.getName().substring(1, field.getName().length());
//            String methodName = new StringBuffer(SET).append(startName.toUpperCase()).append(endName).toString();
//            sethodMap.put(excel.index(), methodName);
//            startRowNameMap.put(excel.index(), excel.name());
//            fieldIsMustMap.put(excel.index(), excel.isMust());
//        }
//
//
//        Workbook wb = WorkbookFactory.create(file.getInputStream());
//        Sheet sheet = wb.getSheetAt(0);
//        Row sheetRow = sheet.getRow(0);
//        for (Cell cell : sheetRow) {
//            Integer columnIndex = cell.getColumnIndex();
//            String value = cell.getStringCellValue();
//            String name = startRowNameMap.get(columnIndex);
//            if (name == null) throw new Exception("Do not move the columns!");
//            if (!name.equals(value)) throw new Exception("excel verify fail " + name + " column is deleted!");
//        }
//
//        sheet.removeRow(sheetRow);
//        List<T> models = new ArrayList<>();
//        for (Row row : sheet) {
//            if (row == null || !checkRow(row)) continue;
//            T obj = c.newInstance();
//            Class excelModelClass = obj.getClass();
//            startRowNameMap.entrySet().forEach(x -> {
//                Integer index = x.getKey();
//                Cell cell = row.getCell(index);
//                String methodName = sethodMap.get(index);
//                if (StringUtils.isEmpty(methodName)) return;
//                List<Method> methods = Arrays.stream(excelModelClass.getMethods())
//                        .filter(m -> m.getName().startsWith(SET)).collect(Collectors.toList());
//                String rowName = startRowNameMap.get(index);
//                for (Method method : methods) {
//                    if (!method.getName().startsWith(methodName)) continue;
//                    String value = null;
//                    try {
//                        value = valueCheck(cell, rowName, fieldIsMustMap.get(index));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    methodInvokeHandler(obj, method, value);
//                }
//            });
//            models.add(obj);
//        }
//        return models;
//    }
//
//    private static String valueCheck(Cell cell, String rowName, Boolean isMust) throws Exception {
//        if (cell == null && isMust) {
//            throw new Exception(rowName + " can not be empty!" );
//        }
//        if (cell == null) return null;
//        cell.setCellType(CellType.STRING);
//        String value = cell.getStringCellValue();
//        if ((value == null || value.trim().isEmpty()) && isMust) {
//            throw new Exception(rowName + " can not be empty!" );
//        }
//        return value;
//    }
//
//    private static void methodInvokeHandler(Object obj, Method method, String value) {
//        Class<?> parameterType = method.getParameterTypes()[0];
//        try {
//            if (parameterType == null) {
//                method.invoke(obj);
//                return;
//            }
//            String name = parameterType.getName();
//            if (name.equals(String.class.getName())) {
//                method.invoke(obj, value);
//                return;
//            }
//            if(name.equals(Boolean.class.getName())) {
//                method.invoke(obj, Boolean.valueOf(value));
//            }
//            if(name.equals(Integer.class.getName())) {
//                method.invoke(obj, Integer.valueOf(value));
//            }
//            if (name.equals(Long.class.getName())) {
//                method.invoke(obj, Long.valueOf(value));
//                return;
//            }
//            if (name.equals(Double.class.getName())) {
//                method.invoke(obj, Double.valueOf(value));
//            }
//            if(name.equals(Gender.class.getName())) {
//                method.invoke(obj, Gender.valueOf(value));
//            }
//            if(name.equals(Race.class.getName())) {
//                method.invoke(obj, Race.valueOf(value));
//            }
//            if(name.equals(RecipientStatus.class.getName())) {
//                method.invoke(obj, RecipientStatus.valueOf(value));
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean checkRow(Row row) {
//        try {
//            if (row == null) return false;
//            short firstCellNum = row.getFirstCellNum();
//            short lastCellNum = row.getLastCellNum();
//            if (firstCellNum < 0 && lastCellNum < 0) return false;
//            if (firstCellNum != 0) {
//                for (short i = firstCellNum; i < lastCellNum; i++) {
//                    Cell cell = row.getCell(i);
//                    String cellValue = cell.getStringCellValue();
//                    if (!StringUtils.isBlank(cellValue)) return true;
//                }
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            return true;
//        }
//    }
//
//}
