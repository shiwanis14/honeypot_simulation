package Utility;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author SANCHITA SETH
 */
public class File_Details_All 
{
    public static String file_details(String field)
    {
        String data = null;
        String general_file_path="G:\\College\\Academics\\Semester VII\\NS\\Project\\Honeypot_NSProject\\src\\Resources\\";
        
        String SQL_Password = "Sanchita1995"; 
        String Database_Name = "honeypot";
    
        Map <String,String> file_details_map = new TreeMap <String,String>();
        
        file_details_map.put("Excel_Traffic_Rows","Excel_Traffic_Rows.xlsx"); //Excel File keeping track of data
        file_details_map.put("ML_Output_File","ML_Output.txt"); //Result of ML
        file_details_map.put("ML_CSV_File","Machine_Python.csv"); //CSV file for Python work to be received at Rheeya's End
        file_details_map.put("Attack_Log_File_Path","Attack_Log.txt"); //Analysis of Netstat Output File
        file_details_map.put("Dump_Data","Dump_Data.xlsx"); // Dump Data
        file_details_map.put("Netstat_File_Path","Netstat_Output.txt");//Netstat Output File
        
        Set <String> keys = file_details_map.keySet();
        
        if(keys.contains(field))
            data = general_file_path.concat(file_details_map.get(field));
        else
        {
            switch (field) {
                case "SQL_Password":
                    data=SQL_Password;
                    break;
                case "Database_Name":
                    data=Database_Name;
                    break;
            }
        }              
        return data;
    }    
}
