package com.fms.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel2JSONHelper {
    //常亮，用作第一种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_Z=1;
    //第二种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_S=2;
    public static void main(String[] args) {
        File dir = new File("e:\\2003.xls");
        Excel2JSONHelper excelHelper = getExcel2JSONHelper();
        //dir文件，0代表是第一行为保存到数据库或者实体类的表头，一般为英文的字符串，2代表是第二种模板，
        JSONArray jsonArray = excelHelper.readExcle(dir, 0, 2);
        System.out.println(jsonArray.toString());;
    }

    /**
     *
     * 获取一个实例
     */
    private static Excel2JSONHelper getExcel2JSONHelper(){
        return new Excel2JSONHelper();
    }

    private boolean fileNameFileter(File file){
        boolean endsWith = false;
        if(file != null){
            String fileName = file.getName();
            endsWith = fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
        }
        return endsWith;
    }

    private Row getHeaderRow(Sheet sheet, int index){
        Row headerRow = null;
        if(sheet!=null){
            headerRow = sheet.getRow(index);
        }
        return headerRow;
    }

    private Object getCellValue(Row row,int cellIndex,FormulaEvaluator formula){
        Cell cell = row.getCell(cellIndex);
        if(cell != null){
            switch (cell.getCellType()) {
                //String类型
                case Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString();

                //number类型
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().getTime();
                    } else {
                        return cell.getNumericCellValue();
                    }
                    //boolean类型
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                //公式
                case Cell.CELL_TYPE_FORMULA:
                    return formula.evaluate(cell).getNumberValue();
                default:
                    return null;
            }
        }
        return null;
    }



    private String getHeaderCellValue(Row headerRow,int cellIndex,int type){
        Cell cell = headerRow.getCell(cellIndex);
        String headerValue = null;
        if(cell != null){
            //第一种模板类型
            if(type == HEADER_VALUE_TYPE_Z){
                headerValue = cell.getRichStringCellValue().getString();
                int l_bracket = headerValue.indexOf("（");
                int r_bracket = headerValue.indexOf("）");
                if(l_bracket == -1){
                    l_bracket = headerValue.indexOf("(");
                }
                if(r_bracket == -1){
                    r_bracket = headerValue.indexOf(")");
                }
                headerValue = headerValue.substring(l_bracket+1, r_bracket);
            }else if(type == HEADER_VALUE_TYPE_S){
                //第二种模板类型
                headerValue = cell.getRichStringCellValue().getString();
            }
        }
        return headerValue;
    }

    public JSONArray readExcle(File file, int headerIndex, int headType){
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        if(!fileNameFileter(file)){
            return null;
        }else{
            try {
                //加载excel表格
                WorkbookFactory wbFactory = new WorkbookFactory();
                Workbook wb = wbFactory.create(file);
                //读取第一个sheet页
                Sheet sheet = wb.getSheetAt(0);
                //读取表头行
                Row headerRow = getHeaderRow(sheet, headerIndex);
                //读取数据
                FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();
                for(int r = headerIndex+1; r<= sheet.getLastRowNum();r++){
                    Row dataRow = sheet.getRow(r);
                    Map<String, Object> map = new HashMap<String, Object>();
                    for(int h = 0; h<dataRow.getLastCellNum();h++){
                        //表头为key
                        String key = getHeaderCellValue(headerRow,h,headType);
                        //数据为value
                        Object value = getCellValue(dataRow, h, formula);
                        if(!key.equals("") && !key.equals("null") && key != null ){
                            map.put(key, value);
                        }
                    }
                    lists.add(map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONArray jsonArray = JSONArray.parseArray(lists.toString());
        return jsonArray;
    }
}