/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Honeypot_Code;

import static Utility.File_Details_All.file_details;
import Utility.File_Util;
import Utility.Socket_Programming;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANCHITA SETH
 */
public class ML_Client 
{
    public static void main(String args[])
    {
        Socket sock = new Socket_Programming().File_Client_Socket(3000);
                               // reading from keyboard (keyRead object)
        Scanner sc = new Scanner(System.in);
        String msg;
        while(true)
        {
            try {
                System.out.println("Enter exit if operations are done, else enter continue");
                msg = sc.nextLine();
                if(msg.equalsIgnoreCase("Exit"))
                {
                    System.out.println("------------------------------------------");
                    sock.close();
                    break;
                }
                File_Util.receiveFile(sock,file_details("ML_CSV_File"));
                System.out.println("Receieved file.....");
                TimeUnit.SECONDS.sleep(30);
                Socket sock2 = new Socket_Programming().File_Client_Socket(3001);
                System.out.println("Sending Files....");
                File_Util.sendFile(sock2,file_details("ML_Output_File"));
                System.out.println("Sent files.......");
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ML_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
}
