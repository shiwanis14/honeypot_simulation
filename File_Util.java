/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author SANCHITA SETH
 */
public class File_Util 
{
      public static void sendFile(Socket s,String file) throws IOException 
    {
	byte [] bytearray = new byte [(int)file.length()];
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        bin.read(bytearray,0,bytearray.length);

        OutputStream os = s.getOutputStream();
        System.out.println("In Util");
        os.write(bytearray,0,bytearray.length);
        os.flush();
        os.close();
        bin.close();
    }
    
    public static void receiveFile(Socket clientSock, String file_name) throws IOException 
    {
	int filesize=4145166; //65 one record size
        int bytesRead;      //bytesRead contain the current statistics of the bytes read from the input channel ie inputstream
        int currentTot; //currentTot contains the total number of bytes read.
        byte [] bytearray_output = new byte [filesize];
        
        InputStream is = clientSock.getInputStream();
        FileOutputStream fos = new FileOutputStream(file_name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(bytearray_output,0,bytearray_output.length);

        currentTot = bytesRead;
        do
        {
            bytesRead = is.read(bytearray_output, currentTot, (bytearray_output.length-currentTot));
            if(bytesRead >= 0)
                currentTot += bytesRead;
        } while(bytesRead > -1); 
        System.out.println("The data of the file is:"+bytearray_output.toString());
        String s = new String(bytearray_output);
        System.out.println("The data of the file is:"+s);
        bos.write(bytearray_output, 0 , currentTot);
        bos.flush();
        bos.close();

        
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while(line!=null)
        {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String total = sb.toString();
        br.close();
        
        PrintWriter pw = new PrintWriter(file_name);
        pw.println(total);
        pw.close();
        
    }
}
