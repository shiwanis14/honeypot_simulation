/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Honeypot_Code;

import Utility.SQLUtil;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import Utility.Socket_Programming;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Sanchita & Saumya
 */
public class Honeypot_Implementation 
{
    public static void main(String args[])
    {
        String line,source_ip_login;
        ServerSocket server_socket = new Socket_Programming().Server_Side_Socket();
        Socket client_socket = null;
        try
        {
       //Server is running always. This is done using this while(true) loop
            while(true)
            {
                Attack_Detection ad = new Attack_Detection();
                //Reading the message from the client
                client_socket = server_socket.accept();
                source_ip_login=client_socket.getRemoteSocketAddress().toString();
                ad.attack_detect(source_ip_login.substring(1, (source_ip_login.length()-1)));//Attack Detection w/o ML
                
                
                DataInputStream dis = new DataInputStream(client_socket.getInputStream());
                PrintStream os = new PrintStream(client_socket.getOutputStream());
                while(true)
                {
                    line=(String)dis.readUTF();
                    if(SQL_Credential_Check(line) && !AttackDetection_ML.attack_detect_ML(source_ip_login.substring(1, (source_ip_login.length()-1))))
                    {
                       os.println("Accept");
                    }
                    else
                    {
                        os.println("Reject");
                    }
                    break;
                }
                os.println("END");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                client_socket.close();
                server_socket.close();
            }
            catch(Exception e){e.printStackTrace();}
        }
    }
    
    private static boolean SQL_Credential_Check(String data)
    {
        boolean flag = false;
        String [] user_data = data.split("\t");
        SQLUtil obj = new SQLUtil();
        String sql_query = "Select password from users where username = ?";     
        ArrayList list = new ArrayList();
        list.add(user_data[0]);
        try
        {
            ResultSet rs = obj.getData(sql_query, list);
            if (rs.next())
            {
                String pwd = rs.getString("password");
                if(pwd.equals(user_data[1]))
                {
                    System.out.println("Successful credentials.");
                    flag = true;
                }
                else
                {
                    System.out.println("Wrong credentials.");
                    flag = false;
                }
                
            }
        }
        catch (ClassNotFoundException | SQLException | HeadlessException e)
        {
            System.err.println("Exception has Occured!");
            e.printStackTrace();
        }
        finally
        {
            obj.close_connection();
        }
        return flag;
    }
        
}
