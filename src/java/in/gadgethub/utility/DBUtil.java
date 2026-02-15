/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lenovo
 */
public class DBUtil {
   private static Connection conn;
   //connection opening 
   public static void openConnection(String dbUrl, String username, String password){
        if(conn==null){
       try{
          
           conn=DriverManager.getConnection(dbUrl,username,password);
           System.out.println("Connection open!");
           
       }catch(SQLException ex){
           System.out.println("Error in Opening Connection");
           ex.printStackTrace();
       }
   }
   }
   //connection closing
   public static void closeConnection(){
       if(conn!=null){
          try{
              conn.close();
          }catch(SQLException ex){
           System.out.println("Error in Closing Connection");
           ex.printStackTrace();
       } 
       }
     
   }
   //provide connection
   public static Connection provideConnection(){
       return conn;
   }
   public static void closeResultset(ResultSet rs){
       if(rs!=null){
           try{
               rs.close();
           }catch(SQLException ex){
           System.out.println("Error in Closing ResultSet");
           ex.printStackTrace();
       } 

       }
   }
   public static void closeStatement(Statement st){
       if(st!=null){
           try{
               st.close();
           }catch(SQLException ex){
           System.out.println("Error in Closing Statement");
           ex.printStackTrace();
       } 
       }
   }
}
