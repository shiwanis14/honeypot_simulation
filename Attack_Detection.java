/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Honeypot_Code;

import static Utility.File_Details_All.file_details;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sanchita Seth
 */
public class Attack_Detection 
{
    static int count_tcp,count_cw,count_tw;
    public void attack_detect(String source_ip_Login) throws FileNotFoundException
    {
        final String cmd = "netstat -ano";
        try 
        {
            File file = new File(file_details("Netstat_File_Path"));
            if (file.exists())
            {
                file.delete();
                file.createNewFile();
            }
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream in = process.getInputStream();
            byte[] buf = new byte[256];
            OutputStream outputConnectionsToFile = new FileOutputStream(file);
            int numbytes=0;
            while ((numbytes = in.read(buf, 0, 256)) != -1) {
                outputConnectionsToFile.write(buf, 0, numbytes);
            }
            System.out.println("Netstat Output File is present at "+file.getAbsolutePath());
            if(file.exists())
            {
                Reader r = new FileReader(file);
                BufferedReader br = new BufferedReader(r);
                String line;
                try {
                    while((line=br.readLine())!=null)
                    {
                        if (line.contains("TCP"))
                        {
                            count_tcp++;
                            if(line.contains("CLOSE_WAIT"))
                                count_cw++;
                            else if (line.contains("TIME_WAIT"))
                                count_tw++;
                        } 
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Attack_Detection.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(count_tcp>50||count_cw>5||count_tw>5)
                    System.out.println("DOS Attack detected!!");
                else
                    System.out.println("Operations are normal.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            File f = new File (file_details("Attack_Log_File_Path"));
            System.out.println("Attack Log File is present at "+f.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write("-------------------------------------------------------------\n");
            bw.write(new Date().toString()+"\n");
            bw.write("1. TCP connections are: " + count_tcp+"\n");
            bw.write("2. TCP + CLOSE_WAIT connections are: " + count_cw+"\n");
            bw.write("3. TCP + TIME_WAIT connections are: " + count_tw+"\n");
            bw.write("4. Source IP of Login by User is: " + source_ip_Login+"\n");
            bw.newLine();
            bw.flush();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
    }
    
}
