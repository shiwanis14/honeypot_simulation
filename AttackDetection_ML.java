/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Honeypot_Code;

import static Utility.File_Details_All.file_details;
import Utility.ML_Server;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANCHITA SETH
 */
public class AttackDetection_ML 
{
    public static boolean attack_detect_ML(String ip)
    {
        FileWriter fw = null;
        boolean flag =false;
        try 
        {
            fw = new FileWriter(file_details("Attack_Log_File_Path"),true);
            String data = ML_Server.CSVFile_server();
            if(data.startsWith("true"))
            {
                System.out.println("Attack Detected!!!!!!! :'( ");
                String attack_data[] = data.split(",");
                System.out.println("Attack Details are logged into "+file_details("Attack_Log_File_Path"));
                fw.write("Attack was Detected. The details are:");
                fw.write("\n1.Attack Type: "+attack_data[1]);
                fw.write("\n2.Attack Group: "+attack_data[2]);
                fw.write("3.IP Address: "+ip+"\n");
                flag = true;
            }
            else
                System.out.println("No Attack Detected. :D ");
            fw.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(AttackDetection_ML.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return flag;
    }
}
