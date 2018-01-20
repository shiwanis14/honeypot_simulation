/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

//import Honeypot_Code.ML_Client;
import static Utility.File_Details_All.file_details;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANCHITA SETH
 */
public class ML_Server 
{   
    public static String CSVFile_server()
    {  
      CSV_File_Generator.Traffic_Data();
      Scanner sc=new Scanner(System.in);
      String msg,total=null,File_Data=null;
      while(true)
      {
            try {
                System.out.println("Enter continue to continue or exit if file transfer is done : ");
                msg=sc.nextLine();
                if(msg.equalsIgnoreCase("exit"))
                {
                    System.out.println("------------------------------------");
                    break;
                }
                    
                   // br.close();
                ServerSocket sersock1 = new Socket_Programming().File_Server_Side_Socket(3000);
                Socket sock = sersock1.accept( );
                
                System.out.println("Server  ready ...............");
                File_Util.sendFile(sock, file_details("ML_CSV_File"));
                System.out.println("Machine_Python.csv file sent ................");
                TimeUnit.SECONDS.sleep(30);
                
                ServerSocket sersock2 = new Socket_Programming().File_Server_Side_Socket(3001);
                Socket sock1 = sersock2.accept( );
                File_Util.receiveFile(sock1,file_details("ML_Output_File"));
                
                System.out.println("ML_Output.txt file received ................");
                
                    BufferedReader br = new BufferedReader(new FileReader(file_details("ML_Output_File")));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while(line!=null)
                    {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    total = sb.toString();
                   // br.close();
                    
                   
                    File_Data = total;
            } catch (IOException ex) {
                Logger.getLogger(ML_Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ML_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      return File_Data;
    }
}
