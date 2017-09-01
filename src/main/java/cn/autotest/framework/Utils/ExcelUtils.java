package cn.autotest.framework.Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by MingfengMa .
 * Data   : 2017/5/5
 * Author : mark
 * Desc   :
 */

public class ExcelUtils {
    private HSSFWorkbook workbook = null;
    Logger logger = LoggerFactory.getLogger(ExcelUtils.class);


    /**
     * 创建excel文件
     * @param fileDir
     * @param filename
     * @param sheetName
     * @param titleRow
     * @return
     */
    public String createExcel(String fileDir,String filename,String sheetName,String[] titleRow){
        if (Objects.equals(fileDir, "") || sheetName.equals("") || titleRow.length<=0){
            throw new RuntimeException("Input parameter is incorrect");
        }
        String filepath = fileDir.concat(filename).concat(".xls");
        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        FileOutputStream outputStream = null;
        try {
            Row row = workbook.getSheet(sheetName).createRow(0);
            for (int i = 0 ; i<titleRow.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(titleRow[i]);
            }
            outputStream = new FileOutputStream(filepath);
            workbook.write(outputStream);
            logger.debug("file path {}",filepath);
            File file = new File(filepath);
            if (file.exists()){
                logger.info("file is created at {}",filepath);
                return filepath;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null){
                    outputStream.close();
                }
                if (workbook!= null){
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String readExcel(String fileDir,String sheet) throws FileNotFoundException {
        FileInputStream inputStream;
        XSSFWorkbook xssfWorkbook;
        try {
            inputStream = new FileInputStream(new File(fileDir));
            xssfWorkbook = new XSSFWorkbook(inputStream);
            int sheets = xssfWorkbook.getNumberOfSheets();
            logger.info("sheet number : {} " , sheets);
            XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheet);
            logger.info(String.valueOf(xssfSheet.getRow(0).getCell(2).getStringCellValue()));
            Iterator<Row> it = xssfSheet.rowIterator();
            while (it.hasNext()){
                Row row = it.next();

                logger.info("column name : {}" ,row.getCell(2).getStringCellValue());
//                logger.info("row data : {}",row.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }


}
