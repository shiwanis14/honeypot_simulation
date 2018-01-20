
/**
 *
 * @author SANCHITA SETH, BTech CS (2013-2017)
 */

package Utility;

import static Utility.File_Details_All.file_details;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLUtil 
{
    private final String driver = "com.mysql.jdbc.Driver";
    private final String user = "root";
    private final String password = file_details("SQL_Password"); //Change to Your pass word of SQL
    private final String url = "jdbc:mysql://localhost:3306/"+file_details("Database_Name"); //Path of Database
    private Connection con;
    
    private Connection get_connection() throws ClassNotFoundException, SQLException 
    {
        Class.forName(driver);
        con = (Connection) DriverManager.getConnection(url, user, password);
        return con;
    }
    
    public int ins_up_del(String sql_query, List alist) throws SQLException, ClassNotFoundException 
    {
        con=get_connection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql_query);
        if (alist!=null && alist.size()>0)
        {
            for(int i=0; i<alist.size();i++)
                pst.setObject(i+1, alist.get(i));
        }
        return pst.executeUpdate();
    }
    
    public ResultSet getData(String sql_query, List alist) throws ClassNotFoundException, SQLException
    {
        con=get_connection();
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql_query);
         if (alist!=null && alist.size()>0)
        {
            for(int i=0; i<alist.size();i++)
                pst.setObject(i+1, alist.get(i));
        }
         return pst.executeQuery();
    }
    
    public void close_connection()
    {
        try
        {
            if(con!=null)
            {
                con.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
