/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Honeypot_Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Saumya Shandilya
 */
public class Attack_Modelling 
{
    private final String path = "C:/Program Files (x86)/Nmap/";
    public void create_attack_dos(String source_ip, String dest_ip)
    {
        try 
        { 
            ProcessBuilder pb = new ProcessBuilder("nping", "-c 50 --source-ip "+source_ip+" --tcp -p 80,433,1500,1600,1200 "+dest_ip+" --tr");
            pb.directory(new File (path));
            System.out.println("Directory: "+pb.directory());
            Process p = pb.start();
            p.waitFor(); 
            BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
            String line=reader.readLine(); 
            while(line!=null) 
            { 
                System.out.println(line); 
                line=reader.readLine(); 
            } 

        } 
        catch(IOException | InterruptedException e1) {System.out.println(e1.toString());} 
        System.out.println("Done"); 
    } 
}
