/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import static Utility.File_Details_All.file_details;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SANCHITA SETH
 */
public class CSV_File_Generator 
{
    public static void Traffic_Data ()
    {
        Create_Excel_File();
        File csv_file = new File(file_details("ML_CSV_File"));
        try {
            FileInputStream fis = new FileInputStream(new File(file_details("Excel_Traffic_Rows")));
            XSSFWorkbook workbook1 = new XSSFWorkbook(fis);
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            
            int last_row_index = sheet1.getLastRowNum();
            XSSFRow last_row = sheet1.getRow(last_row_index);
            
            
            ArrayList<String> data = new ArrayList<String>();
            String str = "";
            StringBuilder sb = new StringBuilder();
            Iterator<Cell> cellIterator = last_row.cellIterator();
            while (cellIterator.hasNext()) 
            {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) 
                {
                    case Cell.CELL_TYPE_NUMERIC:
                        str = cell.getNumericCellValue()+",";
                        break;
                    case Cell.CELL_TYPE_STRING:
                        str = cell.getStringCellValue()+",";
                        break;
                }              
                sb.append(str);
            }
            data.add(sb.substring(0, sb.length()-1));
            PrintWriter pw = new PrintWriter(csv_file);
            pw.write(data.get(0)+"\n");
            pw.close();
            fis.close();
            System.out.println("Data written in Sample File successfully.");
           
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(CSV_File_Generator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(CSV_File_Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void Create_Excel_File()
    {
          try {
            // get input excel files
            FileInputStream excellFile1 = new FileInputStream(new File(file_details("Dump_Data")));
            FileInputStream excellFile2 = new FileInputStream(new File(file_details("Excel_Traffic_Rows")));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Compare sheets
            compareTwoSheets(sheet1, sheet2);
            
            //close files
            excellFile1.close();
            excellFile2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Compare Two Sheets
    public static void compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) {
        int firstRow1 = sheet1.getFirstRowNum();
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        int i;
        for(i=firstRow1; i <= lastRow1; i++) {
            
            
            XSSFRow row1 = sheet1.getRow(i);
            XSSFRow row2 = sheet2.getRow(i);
            if(!compareTwoRows(row1, row2)) {
                equalSheets = false;
                break;
            }
        }
        if(!equalSheets)
        {
            write_single_row(sheet1,sheet2,i);
        }
       
    }

    
    public static void write_single_row(XSSFSheet original_sheet, XSSFSheet resultSheet,int index)
    {
        FileOutputStream out = null;
        try {
            
            out = new FileOutputStream(new File(file_details("Excel_Traffic_Rows")));
            
            int col_index;
            //Create blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet intermediate = workbook.createSheet("Test Data");
            //Create row object
            Row row;
            XSSFRow intermediate_row;
            
            XSSFRow actual_row = original_sheet.getRow(index);
            
            //This data needs to be written (Object[])
            TreeMap < String, TreeMap<String,Cell> > row_map = new TreeMap < String, TreeMap<String, Cell> >();
            
            if(index == 0)
            {
                TreeMap <String, Cell> cols = new TreeMap<String, Cell>();  
            
                XSSFRow temp = intermediate.createRow(index);
                Iterator<Cell> cellIterator = actual_row.cellIterator();
                int i = 1;
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    cols.put(Integer.toString(i++),cell);
                }
                row_map.put("0",cols);
                          
                Set < String > keyid = row_map.get("0").keySet();
                int cellid = 0;
                for (String key : keyid)
                {
                    Cell original = cols.get(key);
                    Cell cell = temp.createCell(cellid++);
                    switch(original.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            cell.setCellValue(original.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cell.setCellValue(original.getStringCellValue());
                            break;
                    }
                }
                workbook.write(out);
                row_map.clear();
            }
            else
            {
                int count=0;
                while(count<index) 
                {
                    Iterator<Row> rowIterator = resultSheet.iterator();
                    while (rowIterator.hasNext()) 
                    {
                        //Reading a row from the existing result sheet
                        TreeMap <String,Cell> data_row = new TreeMap<String,Cell>();
                        row = rowIterator.next();
                        Iterator<Cell> cell = row.cellIterator();
                        col_index=0;
                        while (cell.hasNext())
                        {
                            Cell c = cell.next();
                            data_row.put(Integer.toString(col_index++),c);
                        }

                        row_map.put(Integer.toString(count), data_row);
                        count++;
                    }
                    //writing the row read into the new workbook(intermediate)
                    Set < String > keyid = row_map.keySet();
                    for (String key : keyid)
                    {
                        int column_counter=0;
                        intermediate_row = intermediate.createRow(Integer.parseInt(key));
                        TreeMap <String,Cell> map = row_map.get(key);
                        Set <String> row_data = map.keySet();
                        for(String cell_data: row_data)
                        {
                            Cell original = map.get(cell_data);
                            Cell new_cell = intermediate_row.createCell(column_counter++);
                            switch(original.getCellType())
                            {
                                case Cell.CELL_TYPE_NUMERIC:
                                    new_cell.setCellValue(original.getNumericCellValue());
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    new_cell.setCellValue(original.getStringCellValue());
                                    break;
                            }
                        }
                    }
                }
                XSSFRow temp = intermediate.createRow(index);
                Iterator<Cell> cellIterator = actual_row.cellIterator();
                TreeMap<String, Cell> required_data = new TreeMap<String,Cell>();
                
                int i = 0;
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    required_data.put(Integer.toString(i++),cell);
                }
                row_map.put(Integer.toString(index), required_data);
                
                required_data = row_map.get(Integer.toString(index));
                
                Set < String > keyid = required_data.keySet();
                int cellid = 0;
                for (String key : keyid)
                {
                    Cell original = required_data.get(key);
                    Cell cell = temp.createCell(cellid++);
                    switch(original.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            cell.setCellValue(original.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cell.setCellValue(original.getStringCellValue());
                            break;
                    }
                }
                workbook.write(out);
                out.flush();
                row_map.clear();
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSV_File_Generator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSV_File_Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Traffic Data is: "+ index +" row.");
    }
    
        
    
    // Compare Two Rows
    public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
        if((row1 == null) && (row2 == null)) {
            return true;
        } else if((row1 == null) || (row2 == null)) {
            return false;
        }
        
        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;
        
        // Compare all cells in a row
        for(int i=firstCell1; i <= lastCell1; i++) {
            XSSFCell cell1 = row1.getCell(i);
            XSSFCell cell2 = row2.getCell(i);
            if(!compareTwoCells(cell1, cell2)) {
                equalRows = false;
                break;
            } 
        }
        return equalRows;
    }

    // Compare Two Cells
    public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
        if((cell1 == null) && (cell2 == null)) {
            return true;
        } else if((cell1 == null) || (cell2 == null)) {
            return false;
        }
        
        boolean equalCells = false;
        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) {
            if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
                // Compare cells based on its type
                switch (cell1.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (cell1.getNumericCellValue() == cell2
                            .getNumericCellValue()) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    if (cell1.getStringCellValue().equals(cell2
                            .getStringCellValue())) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue() == cell2
                            .getBooleanCellValue()) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                        equalCells = true;
                    }
                    break;
                default:
                    if (cell1.getStringCellValue().equals(
                            cell2.getStringCellValue())) {
                        equalCells = true;
                    }
                    break;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return equalCells;
    }
}
