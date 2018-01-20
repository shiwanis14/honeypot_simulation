package Utility;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANCHITA SETH
 */
public class Socket_Programming 
{
    int port = 6876;
    public Socket Client_Socket() 
    {
        String host = IP_Details.Host_IP_Client_Socket;
        Socket client_socket=null;
        try {
            client_socket = new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(Socket_Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
        return client_socket;
    }
    
    public ServerSocket Server_Side_Socket()
    {
        ServerSocket server_socket=null;
        try 
        {
            server_socket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port "+port);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Socket_Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
        return server_socket;
    }
    
    public Socket File_Client_Socket(int port_num)
    {
        String host = IP_Details.File_IP_Client_Socket;
        Socket client_socket=null;
        try {
            client_socket = new Socket(host, port_num);
        } catch (IOException ex) {
            Logger.getLogger(Socket_Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
        return client_socket;
    }
    
     public ServerSocket File_Server_Side_Socket(int port_num)
    {
        ServerSocket server_socket=null;
        try 
        {
            server_socket = new ServerSocket(port_num);
            System.out.println("Server Started and listening to the port "+port_num);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Socket_Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
        return server_socket;
    }
}
